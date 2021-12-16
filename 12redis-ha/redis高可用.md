# redis高可用

操作环境为Debian11，使用root登录并`cd /opt`

下载redis

```
wget https://github.com/redis/redis/archive/refs/tags/6.2.6.tar.gz
```

解压

```
tar xzvf 6.2.6.tar.gz
```

进入目录并编译

````
cd /opt/redis-6.2.6
apt install -y build-essential tcl
make
make test
````



## 主从复制

### 配置

主节点配置文件`redis-1.conf`，端口6379.

从节点配置文件`redis-2.conf`，端口6380，配置文件的最后，加入一行`slaveof 127.0.0.1 6379`。

启动主节点：

```
src/redis-server redis-1.conf
```

启动从节点：

```
src/redis-server redis-2.conf
```

### 验证

1. 在终端连接从节点`src/redis-cli -p 6380`，执行`get thekey`，结果为空；
2. 在终端连接主节点`src/redis-cli -p 6379`，执行`set thekey 123`；
3. 再次在终端连接从节点，执行`get thekey`，结果为`"123"`；



## redis sentinel

### 配置

在上面的基础上，再增加一个从节点配置文件`redis-3.conf`，端口6381，配置文件的最后，加入一行`slaveof 127.0.0.1 6379`。

创建3个sentinel配置文件（sentinel-1.conf/sentinel-2.conf/sentinel-3.conf），端口分别为26379/26380/26381，并修改配置`daemonize yes`。

分别启动3个哨兵节点：

```
src/redis-sentinel sentinel-1.conf # 或者 redis-server sentinel-1.conf --sentinel
src/redis-sentinel sentinel-2.conf
src/redis-sentinel sentinel-3.conf
```

在终端连接26379哨兵节点`src/redis-cli -p 26379`，输入`info Sentinel`，结果如下：

```
# Sentinel
sentinel_masters:1
sentinel_tilt:0
sentinel_running_scripts:0
sentinel_scripts_queue_length:0
sentinel_simulate_failure_flags:0
master0:name=mymaster,status=ok,address=127.0.0.1:6379,slaves=2,sentinels=3
```

### 验证故障转移

停掉6379节点，立刻再次输入`info Sentinel`，结果暂时没有变化，等待一段时间后，再次输入`info Sentinel`，结果已经变成：

```
# Sentinel
sentinel_masters:1
sentinel_tilt:0
sentinel_running_scripts:0
sentinel_scripts_queue_length:0
sentinel_simulate_failure_flags:0
master0:name=mymaster,status=ok,address=127.0.0.1:6381,slaves=2,sentinels=3
```

此时6381被切换为新的主节点，slaves=2，6379已经变为从节点，不会被客观下线，所以仍然认为其存在。

重启6379节点，在终端连接6381节点`src/redis-cli -p 6381`，输入`info Replication`，结果如下：

```
# Replication
role:master
connected_slaves:2
slave0:ip=127.0.0.1,port=6380,state=online,offset=175867,lag=0
slave1:ip=127.0.0.1,port=6379,state=online,offset=175734,lag=0
master_failover_state:no-failover
master_replid:701d99b9fa0f19345e5d0a35a9caca78dbc1c907
master_replid2:743bfddcb14dccbcae4ee58614831d850db6f583
master_repl_offset:175867
second_repl_offset:70941
repl_backlog_active:1
repl_backlog_size:1048576
repl_backlog_first_byte_offset:1
repl_backlog_histlen:175867
```

可见6379节点已经成为从节点。

### 注意

哨兵只是配置提供者，不是代理。如果客户端在局域网中的另一台主机上，连接到上述配置的哨兵，获取到的主节点地址是`127.0.0.1:63xx`，因而会导致连接失败。



## redis cluster

### 配置

搭建3主3从的集群，3个主节点端口：6001/6002/6003，3个从节点端口：7001/7002/7003。

6001节点配置文件`redis-6001.conf`主要修改内容如下：

```
port 6001
daemonize yes
pidfile "/var/run/redis_6001.pid"
logfile "6001.log"
cluster-enabled yes
cluster-config-file nodes-6001.conf
dbfilename "dump6001.rdb"
```

其他节点类似，只需要替换和端口相关的文本，修改完成后分别启动以上所有节点。

### 节点握手

在终端连接6001节点`src/redis-cli -p 6001`，和其他节点完成握手：

```
cluster meet 127.0.0.1 6002
cluster meet 127.0.0.1 6003
cluster meet 127.0.0.1 7001
cluster meet 127.0.0.1 7002
cluster meet 127.0.0.1 7003
```

完成后输入`cluster nodes`，可以看到所有节点：

```
a23178e387f816c20f471232d73aa0134afbfebe 127.0.0.1:6003@16003 master - 0 1639634222000 2 connected
cdd5a029561ee28acd2128a8477a6b491245ace8 127.0.0.1:6001@16001 myself,master - 0 1639634222000 3 connected
358d1ae9ddbac7931898453fbf9b0d23d9f2bccb 127.0.0.1:7003@17003 master - 0 1639634221000 5 connected
ab5421061e370ef32864282e19eef71e308d8f52 127.0.0.1:7002@17002 master - 0 1639634221056 4 connected
13da7da215eccd7c21e1eb413011c204cec04ffd 127.0.0.1:6002@16002 master - 0 1639634223075 1 connected
dc201ead3bf6ec00531f89d0cf43d613e897100d 127.0.0.1:7001@17001 master - 0 1639634222066 0 connected
```

此时输入`cluster info`，可以看到cluster_state是fail，集群有16384个槽，需要分配给3个主节点，这个值才会变成ok。

### 分配槽

输入`exit`，在终端执行：

```
src/redis-cli -p 6001 cluster addslots {0..5461}
src/redis-cli -p 6002 cluster addslots {5462..10922}
src/redis-cli -p 6003 cluster addslots {10923..16383}
```

### 指定主从关系

根据上面获得的主节点id，在终端执行：

```
src/redis-cli -p 7001 cluster replicate cdd5a029561ee28acd2128a8477a6b491245ace8
src/redis-cli -p 7002 cluster replicate 13da7da215eccd7c21e1eb413011c204cec04ffd
src/redis-cli -p 7003 cluster replicate a23178e387f816c20f471232d73aa0134afbfebe
```

再执行`src/redis-cli -p 6001 cluster nodes`，结果如下：

```
a23178e387f816c20f471232d73aa0134afbfebe 127.0.0.1:6003@16003 master - 0 1639634307000 2 connected 10923-16383
cdd5a029561ee28acd2128a8477a6b491245ace8 127.0.0.1:6001@16001 myself,master - 0 1639634305000 3 connected 0-5461
358d1ae9ddbac7931898453fbf9b0d23d9f2bccb 127.0.0.1:7003@17003 slave a23178e387f816c20f471232d73aa0134afbfebe 0 1639634307000 2 connected
ab5421061e370ef32864282e19eef71e308d8f52 127.0.0.1:7002@17002 slave 13da7da215eccd7c21e1eb413011c204cec04ffd 0 1639634308778 1 connected
13da7da215eccd7c21e1eb413011c204cec04ffd 127.0.0.1:6002@16002 master - 0 1639634307770 1 connected 5462-10922
dc201ead3bf6ec00531f89d0cf43d613e897100d 127.0.0.1:7001@17001 slave cdd5a029561ee28acd2128a8477a6b491245ace8 0 1639634303738 3 connected
```

至此集群搭建完毕，实际操作时把命令中的`127.0.0.1`改为局域网IP即可。
