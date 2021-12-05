package com.emeraldfrost.redispubsub.config;

import com.emeraldfrost.redispubsub.service.RedisMessageSubscriber;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {

	@Bean
	public MessageListenerAdapter messageListener() {
		return new MessageListenerAdapter(new RedisMessageSubscriber());
	}

	@Bean
	public ChannelTopic topic() {
		return new ChannelTopic("pubsub:demo");
	}

	@Bean
	public RedisMessageListenerContainer listenerContainer(RedisConnectionFactory connectionFactory, MessageListenerAdapter messageListener) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(messageListener(), topic());
		return container;
	}
}
