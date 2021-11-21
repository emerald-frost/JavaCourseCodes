package com.emeraldfrost.forexds.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.emeraldfrost.forexds.constants.DB;
import com.emeraldfrost.forexds.core.DataSourcePropertyPayload;
import com.emeraldfrost.forexds.core.MyDynamicDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@RequiredArgsConstructor
@Configuration
@EnableConfigurationProperties(DataSourcePropertyPayload.class)
public class DynamicDataSourceConfig {

	/**
	 * 创建动态数据源
	 */
	@Bean
	@Primary
	public DataSource getDatasource(DataSourcePropertyPayload payload) {
		final MyDynamicDataSource myDynamicDatasource = new MyDynamicDataSource();
		Map<Object, Object> map = new HashMap<>();
		for (Map.Entry<String, DataSourcePropertyPayload.DatasourceProperties> entry : payload.getDatasource().entrySet()) {
			final String key = entry.getKey();
			final DataSourcePropertyPayload.DatasourceProperties properties = entry.getValue();
			final DataSource dataSource = newDataSource(properties);
			map.put(DB.valueOf(key), dataSource);
		}
		myDynamicDatasource.setTargetDataSources(map);
		myDynamicDatasource.setDefaultTargetDataSource(map.get(DB.A));
		return myDynamicDatasource;
	}

	private DataSource newDataSource(DataSourcePropertyPayload.DatasourceProperties properties) {
		final HikariConfig config = new HikariConfig();
		config.setDriverClassName(properties.getDriverClassName());
		config.setJdbcUrl(properties.getUrl());
		config.setUsername(properties.getUsername());
		config.setPassword(properties.getPassword());
		return new HikariDataSource(config);
	}
}
