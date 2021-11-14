package com.emeraldfrost.tccdemoorder;

import org.mybatis.spring.annotation.MapperScan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@MapperScan("com.emeraldfrost.tccdemoorder.mapper")
@EnableDiscoveryClient
@EnableFeignClients
@EnableTransactionManagement
@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class TccDemoOrderApplication {

	public static void main(String[] args) {
		SpringApplication.run(TccDemoOrderApplication.class, args);
	}

}
