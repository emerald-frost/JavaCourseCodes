# dynamic-datasource-v1

插入走了主库。

执行10次查询，两个从库权重分别为3和2，分别查询了6次和4次，

输出如下，符合从库负载权重配置。

```text
2021-11-08 23:49:40.206 DEBUG 16712 --- [           main] c.e.d.c.aspect.DynamicDatasourceAspect   : method: modify, 走主库
插入记录对应的id: 9
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.core.config.SlaveDatasourceRouter  : 从库index: 1
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.c.aspect.DynamicDatasourceAspect   : method: query, 走从库
读取到的最新id: 9
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.core.config.SlaveDatasourceRouter  : 从库index: 0
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.c.aspect.DynamicDatasourceAspect   : method: query, 走从库
读取到的最新id: 9
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.core.config.SlaveDatasourceRouter  : 从库index: 1
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.c.aspect.DynamicDatasourceAspect   : method: query, 走从库
读取到的最新id: 9
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.core.config.SlaveDatasourceRouter  : 从库index: 0
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.c.aspect.DynamicDatasourceAspect   : method: query, 走从库
读取到的最新id: 9
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.core.config.SlaveDatasourceRouter  : 从库index: 1
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.c.aspect.DynamicDatasourceAspect   : method: query, 走从库
读取到的最新id: 9
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.core.config.SlaveDatasourceRouter  : 从库index: 1
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.c.aspect.DynamicDatasourceAspect   : method: query, 走从库
读取到的最新id: 9
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.core.config.SlaveDatasourceRouter  : 从库index: 0
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.c.aspect.DynamicDatasourceAspect   : method: query, 走从库
读取到的最新id: 9
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.core.config.SlaveDatasourceRouter  : 从库index: 1
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.c.aspect.DynamicDatasourceAspect   : method: query, 走从库
读取到的最新id: 9
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.core.config.SlaveDatasourceRouter  : 从库index: 0
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.c.aspect.DynamicDatasourceAspect   : method: query, 走从库
读取到的最新id: 9
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.core.config.SlaveDatasourceRouter  : 从库index: 1
2021-11-08 23:49:40.264 DEBUG 16712 --- [           main] c.e.d.c.aspect.DynamicDatasourceAspect   : method: query, 走从库
读取到的最新id: 9
```
