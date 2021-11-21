package com.emeraldfrost.forexds.core;


import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 加载配置用
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "dynamic-ds")
public class DataSourcePropertyPayload {

	private Map<String, DatasourceProperties> datasource;

	@Getter
	@Setter
	public static class DatasourceProperties {

		private String driverClassName;

		private String url;

		private String username;

		private String password;
	}
}
