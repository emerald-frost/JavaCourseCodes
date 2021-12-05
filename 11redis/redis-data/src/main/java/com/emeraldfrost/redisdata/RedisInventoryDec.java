package com.emeraldfrost.redisdata;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisInventoryDec {

	private static final RedisClient CLIENT;

	private static final StatefulRedisConnection<String, String> CONNECTION;

	private static final String COUNT_KEY = "count";

	static {
		final RedisURI redisURI = RedisURI.builder()
				.withHost("[fe80::1:1]")
				.withPort(6379)
				.build();
		CLIENT = RedisClient.create(redisURI);
		CONNECTION = CLIENT.connect();
	}

	public static void main(String[] args) {
		final Runnable runnable = () -> {
			long count;
			while ((count = testRedisDecr()) >= 0) {
				System.out.println(Thread.currentThread() + "->" + count);
			}
			System.out.println(Thread.currentThread() + " exit");
		};

		// 设置初始库存
		final long inventory = 1000L;
		final RedisCommands<String, String> commands = CONNECTION.sync();
		commands.setex(COUNT_KEY, 100_0000, String.valueOf(inventory));

		new Thread(runnable).start();
		new Thread(runnable).start();
		new Thread(runnable).start();
		new Thread(runnable).start();
		new Thread(runnable).start();
	}

	public static long testRedisDecr() {
		final RedisCommands<String, String> commands = CONNECTION.sync();
		final Long newVal = commands.decr(COUNT_KEY);
		return newVal;
	}
}
