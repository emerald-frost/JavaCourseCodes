package com.emeraldfrost.redisdata;

import java.time.Duration;

import io.lettuce.core.RedisClient;
import io.lettuce.core.RedisURI;
import io.lettuce.core.ScriptOutputType;
import io.lettuce.core.SetArgs;
import io.lettuce.core.api.StatefulRedisConnection;
import io.lettuce.core.api.sync.RedisCommands;

public class RedisLock {

	private static final RedisClient CLIENT;

	private static final StatefulRedisConnection<String, String> CONNECTION;

	static {
		final RedisURI redisURI = RedisURI.builder()
				.withHost("[fe80::1:1]")
				.withPort(6379)
				.build();
		CLIENT = RedisClient.create(redisURI);
		CONNECTION = CLIENT.connect();
	}

	public static void main(String[] args) {
		new Thread(() -> testRedisLock()).start();
		new Thread(() -> testRedisLock()).start();
	}

	public static void testRedisLock() {
		final String lockKey = "lock";
		final String lockVal = Thread.currentThread().getName();
		final RedisCommands<String, String> commands = CONNECTION.sync();

		//5秒超时
		final String set = commands.set(lockKey, lockVal, SetArgs.Builder.nx().px(Duration.ofSeconds(5L)));
		//是否设置成功
		final boolean succeed = "OK".equals(set);
		if (succeed) {
			//读取内容
			final String valFromCache = commands.get(lockKey);
			System.out.println("val from cache: " + valFromCache);

			//释放锁
			Long released = commands.eval(
					"if redis.call('get',KEYS[1])== ARGV[1] " +
							"then return redis.call('del',KEYS[1]) " +
							"else return 0 " +
							"end",
					ScriptOutputType.INTEGER,
					new String[] {lockKey},
					lockVal
			);
			System.out.println("released lock: " + released);
		}
		else {
			System.out.println("already locked");
			try {
				Thread.sleep(1000L * 2);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			testRedisLock();
		}
	}
}
