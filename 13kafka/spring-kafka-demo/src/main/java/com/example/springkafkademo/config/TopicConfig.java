package com.example.springkafkademo.config;

import org.apache.kafka.clients.admin.NewTopic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfig {

	@Bean
	public NewTopic demoTopic() {
		return TopicBuilder.name(Constants.DEMO_TOPIC)
				.partitions(2)
				.replicas(1)
				.compact()
				.build();
	}
}
