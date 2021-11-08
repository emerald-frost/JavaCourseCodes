package com.emeraldfrost.dynamicdatasourcev1.core.config;


import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 加载配置用
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "spring.dynamic")
public class DatasourcePropertyMap {

	private Map<String, DatasourceProperty> datasource;

	@Getter
	@Setter
	public static class DatasourceProperty {

		private String driverClassName;

		private String url;

		private String username;

		private String password;

		private Integer weight;
	}
}
