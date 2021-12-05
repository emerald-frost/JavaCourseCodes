package com.emeraldfrost.redispubsub.service;

import lombok.RequiredArgsConstructor;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RedisMessagePublisher {

	private final StringRedisTemplate redisTemplate;

	private final ChannelTopic topic;

	public void publish(String message) {
		redisTemplate.convertAndSend(topic.getTopic(), message);
	}
}
