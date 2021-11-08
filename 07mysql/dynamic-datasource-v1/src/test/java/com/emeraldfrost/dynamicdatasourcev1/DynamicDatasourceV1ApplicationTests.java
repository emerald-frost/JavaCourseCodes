package com.emeraldfrost.dynamicdatasourcev1;

import com.emeraldfrost.dynamicdatasourcev1.service.T1Service;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DynamicDatasourceV1ApplicationTests {

	@Autowired T1Service service;

	@Test
	void contextLoads() {
		service.modify();

		for (int i = 0; i < 10; i++) {
			service.query();
		}
	}

/*
输出如下，符合从库负载权重配置

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

*/

}
