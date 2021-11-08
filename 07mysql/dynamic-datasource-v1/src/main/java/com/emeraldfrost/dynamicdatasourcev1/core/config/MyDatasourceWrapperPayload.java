package com.emeraldfrost.dynamicdatasourcev1.core.config;

import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 存多个数据源信息的载体，方便其他方法注入
 */
public final class MyDatasourceWrapperPayload {

	private final Set<MyDatasourceWrapper> set = new HashSet<>();

	public void add(MyDatasourceWrapper wrapper) {
		this.set.add(wrapper);
	}

	public Set<MyDatasourceWrapper> get() {
		return this.set;
	}

	@Getter
	@Setter
	@NoArgsConstructor
	@AllArgsConstructor
	public static class MyDatasourceWrapper {

		private String name;

		private Integer weight;

		private DataSource dataSource;

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof MyDatasourceWrapper)) return false;

			MyDatasourceWrapper myDatasourceWrapper = (MyDatasourceWrapper) o;

			return name.equals(myDatasourceWrapper.name);
		}

		@Override
		public int hashCode() {
			return name.hashCode();
		}
	}
}
