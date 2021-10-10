package io.github.emeraldfrost.gateway.router;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RoundRibbonHttpEndpointRouter implements HttpEndpointRouter {

	/**
	 * 每次请求都会重新创建实例，所以将该变量设为静态变量
	 */
	private static final AtomicInteger INDEX_COUNTER = new AtomicInteger(0);

	@Override
	public String route(List<String> endpoints) {
		final int size = endpoints.size();
		final int currentVal = INDEX_COUNTER.getAndUpdate(previousVal -> (previousVal + 1) % size);
		log.debug("router index: {}", currentVal);
		//如果endpoints的长度可能变化，则此处需要判断是否越界
		return endpoints.get(currentVal);
	}
}
