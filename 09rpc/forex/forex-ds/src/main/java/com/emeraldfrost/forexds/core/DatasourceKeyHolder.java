package com.emeraldfrost.forexds.core;

import com.emeraldfrost.forexds.constants.DB;

public class DatasourceKeyHolder {

	private static final ThreadLocal<DB> CURRENT_KEY = new ThreadLocal<>();

	public static void set(DB key) {
		CURRENT_KEY.set(key);
	}

	public static DB get() {
		return CURRENT_KEY.get();
	}

	public static void remove() {
		CURRENT_KEY.remove();
	}
}
