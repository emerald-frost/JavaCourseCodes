package com.emeraldfrost.forexcommon;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableDubbo
@SpringBootApplication
public class ForexCommonApplication {

	public static void main(String[] args) {
		SpringApplication.run(ForexCommonApplication.class, args);
	}

}
