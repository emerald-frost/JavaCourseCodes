package com.emeraldfrost.forexds.core;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MyDynamicDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return DatasourceKeyHolder.get();
	}
}
