package com.example.springkafkademo.controller;

import java.util.List;

import com.example.springkafkademo.config.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.PartitionInfo;

import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
public class MessageController {

	private final KafkaTemplate<String, Object> kafkaTemplate;

	@RequestMapping("/send")
	public ResponseEntity<Void> send(String message) {
		kafkaTemplate.send(Constants.DEMO_TOPIC, message).addCallback(
				success -> {
					final RecordMetadata meta = success.getRecordMetadata();
					final String topic = meta.topic();
					final int partition = meta.partition();
					final long offset = meta.offset();
					log.info("topic: {}, partition: {}, offset: {}", topic, partition, offset);
				},
				failure -> {
					log.error("error: {}", failure.getMessage());
				}
		);
		return ResponseEntity.ok().build();
	}
}
