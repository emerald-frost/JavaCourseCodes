package com.emeraldfrost.dynamicdatasourcev2;

import com.emeraldfrost.dynamicdatasourcev2.service.T1Service;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class DynamicDatasourceV2ApplicationTests {

	@Autowired T1Service service;

	@Test
	void contextLoads() {
		service.modify();

		for (int i = 0; i < 10; i++) {
			service.query();
		}
	}

}
