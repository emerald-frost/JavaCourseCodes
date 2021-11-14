package com.emeraldfrost.tccdemoaccount;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.emeraldfrost.tccdemoaccount.mapper")
@EnableDiscoveryClient
@EnableTransactionManagement
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class TccDemoAccountApplication {

	public static void main(String[] args) {
		SpringApplication.run(TccDemoAccountApplication.class, args);
	}

}
