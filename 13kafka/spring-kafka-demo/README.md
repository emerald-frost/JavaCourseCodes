# spring-kafka-demo

## 环境搭建

使用kafka3.0的KRaft模式(暂时还是preview)创建集群，使用3台主机，系统为Debian11。

IP地址分别为192.168.2.136，192.168.2.137，192.168.2.138。

### 配置
修改`config/kraft/server.properties`，以node1为例：
```properties
process.roles=broker,controller
node.id=1
controller.quorum.voters=1@192.168.2.136:9093,2@192.168.2.137:9093,3@192.168.2.138:9093

listeners=PLAINTEXT://192.168.2.136:9092,CONTROLLER://192.168.2.136:9093
advertised.listeners=PLAINTEXT://192.168.2.136:9092
```
需要指定`node.id`和IP地址。

### 启动

进入`/opt/kafka_2.13-3.0.0/`

生成集群id
```bash
./bin/kafka-storage.sh random-uuid
```
结果为`frQnBcd_SbCHDeYUGCd8Ww`

启动前格式化`Storage Directories`，在每个节点的中执行
```bash
./bin/kafka-storage.sh format -t frQnBcd_SbCHDeYUGCd8Ww -c ./config/kraft/server.properties
```

然后分别启动3个节点
```bash
./bin/kafka-server-start.sh ./config/kraft/server.properties
```
