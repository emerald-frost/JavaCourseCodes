package com.example.springkafkademo.config;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Component;

@Component
public class MessageListener1 {

	@KafkaListener(topicPartitions = @TopicPartition(
			topic = Constants.DEMO_TOPIC,
			partitions = {"1"})
	)
	public void listen(String message) {
		System.out.println("listener1 received: " + message);
	}
}
