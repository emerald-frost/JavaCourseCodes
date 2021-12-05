package com.emeraldfrost.redispubsub.controller;

import com.emeraldfrost.redispubsub.service.RedisMessagePublisher;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PublishController {

	private final RedisMessagePublisher publisher;

	@RequestMapping("/publish")
	public String publish(String message) {
		publisher.publish(message);
		return "published: " + message;
	}
}
