package com.emeraldfrost.dynamicdatasourcev1.core.config;

import com.emeraldfrost.dynamicdatasourcev1.core.constants.Global;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MyDynamicDatasource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		final String key = DatasourceKeyHolder.get();
		return key != null ? key : Global.MASTER;
	}
}
