package com.emeraldfrost.dynamicdatasourcev1.core.config;

public class DatasourceKeyHolder {

	private static final ThreadLocal<String> CURRENT_KEY = new ThreadLocal<>();

	public static void set(String key) {
		CURRENT_KEY.set(key);
	}

	public static String get() {
		return CURRENT_KEY.get();
	}

	public static void remove() {
		CURRENT_KEY.remove();
	}
}
