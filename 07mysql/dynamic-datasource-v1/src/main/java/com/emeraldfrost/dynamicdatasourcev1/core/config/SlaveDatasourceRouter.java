package com.emeraldfrost.dynamicdatasourcev1.core.config;

import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.util.CollectionUtils;

/**
 * 从库路由，根据加权进行负载均衡
 */
@Slf4j
public class SlaveDatasourceRouter {

	/**
	 * 初始权重
	 */
	private final int[] initWeight;

	/**
	 * 权重总和
	 */
	private final int totalWeight;

	/**
	 * 当前权重
	 */
	private final int[] currentWeight;

	/**
	 * 用数组，方便读取
	 */
	private final MyDatasourceWrapperPayload.MyDatasourceWrapper[] slaveMyDatasourceWrappers;

	public SlaveDatasourceRouter(Set<MyDatasourceWrapperPayload.MyDatasourceWrapper> slaveMyDatasourceWrapperSet) {
		if (CollectionUtils.isEmpty(slaveMyDatasourceWrapperSet)) {
			throw new IllegalArgumentException();
		}
		final int size = slaveMyDatasourceWrapperSet.size();
		initWeight = new int[size];
		currentWeight = new int[size];
		slaveMyDatasourceWrappers = new MyDatasourceWrapperPayload.MyDatasourceWrapper[size];

		int i = 0;
		int totalWeightHolder = 0;
		for (MyDatasourceWrapperPayload.MyDatasourceWrapper o : slaveMyDatasourceWrapperSet) {
			initWeight[i] = o.getWeight();
			totalWeightHolder += o.getWeight();
			currentWeight[i] = 0;
			slaveMyDatasourceWrappers[i] = o;
			i++;
		}
		this.totalWeight = totalWeightHolder;
	}

	/**
	 * 使用加权轮询算法，选一个当前权重最大的，返回名称
	 * @return 从库的数据源名称
	 */
	public String route() {
		int max = 0;
		int index = 0;

		for (int i = 0; i < initWeight.length; i++) {
			// 当前权重=当前权重+初始权重
			currentWeight[i] += initWeight[i];

			// 找到当前权重最大的
			if (currentWeight[i] > max) {
				max = currentWeight[i];
				index = i;
			}
		}

		// 将选中节点的当前权重减去总权重
		currentWeight[index] -= totalWeight;

		log.debug("从库index: {}", index);
		return slaveMyDatasourceWrappers[index].getName();
	}
}
