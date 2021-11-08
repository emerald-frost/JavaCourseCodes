package com.emeraldfrost.dynamicdatasourcev1.core.config;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.sql.DataSource;

import com.emeraldfrost.dynamicdatasourcev1.core.constants.Global;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(DatasourcePropertyMap.class)
public class DatasourceConfig {

	private final DatasourcePropertyMap dataSourcePropertyMap;

	/**
	 * 读取配置文件，转换成方便后续操作的类型
	 */
	@Bean
	public MyDatasourceWrapperPayload getMyDatasourceWrapperPayload() {
		MyDatasourceWrapperPayload payload = new MyDatasourceWrapperPayload();
		for (Map.Entry<String, DatasourcePropertyMap.DatasourceProperty> entry : dataSourcePropertyMap.getDatasource().entrySet()) {
			final String name = entry.getKey();
			final DatasourcePropertyMap.DatasourceProperty property = entry.getValue();

			int weight = property.getWeight() != null && property.getWeight() > 0 ? property.getWeight() : 1;

			final HikariConfig config = new HikariConfig();
			config.setDriverClassName(property.getDriverClassName());
			config.setJdbcUrl(property.getUrl());
			config.setUsername(property.getUsername());
			config.setPassword(property.getPassword());
			final HikariDataSource dataSource = new HikariDataSource(config);

			final MyDatasourceWrapperPayload.MyDatasourceWrapper wrapper = new MyDatasourceWrapperPayload.MyDatasourceWrapper(name, weight, dataSource);
			payload.add(wrapper);
		}
		return payload;
	}

	/**
	 * 创建动态数据源
	 */
	@Bean
	@Primary
	public DataSource getDatasource(MyDatasourceWrapperPayload payload) {
		final MyDynamicDatasource myDynamicDatasource = new MyDynamicDatasource();
		Map<Object, Object> map = new HashMap<>(payload.get().size());
		for (MyDatasourceWrapperPayload.MyDatasourceWrapper o : payload.get()) {
			map.put(o.getName(), o.getDataSource());
		}
		myDynamicDatasource.setTargetDataSources(map);
		myDynamicDatasource.setDefaultTargetDataSource(map.get(Global.MASTER));
		return myDynamicDatasource;
	}

	/**
	 * 创建从库路由
	 */
	@Bean
	public SlaveDatasourceRouter getSlaveDatasourceRouter(MyDatasourceWrapperPayload payload) {
		final Set<MyDatasourceWrapperPayload.MyDatasourceWrapper> slaveDsSet = payload.get().stream()
				.filter(o -> !Global.MASTER.equals(o.getName()))
				.collect(Collectors.toSet());
		return new SlaveDatasourceRouter(slaveDsSet);
	}
}
