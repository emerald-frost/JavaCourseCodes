package com.emeraldfrost.dynamicdatasourcev2.service;

import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

/**
 * 修改数据，应该写入主库
 */
@RequiredArgsConstructor
@Service
public class T1Service {

	private final JdbcTemplate jdbcTemplate;

	/**
	 * 写入一条记录，主键自增长
	 *
	 * CREATE TABLE t1 (
	 *   id int(11) NOT NULL AUTO_INCREMENT,
	 *   some_data varchar(100) DEFAULT NULL,
	 *   PRIMARY KEY (id)
	 * )
	 */
	public void modify() {
		final int r = jdbcTemplate.update("insert into t1(some_data) values (?)", "随便写入点什么" + System.currentTimeMillis());
		System.out.println("inserted: " + r);
	}

	/**
	 * 输出最新的id
	 */
	public void query() {
		final String sql = "select id from t1 order by id desc limit 1";
		final Integer id = jdbcTemplate.query(sql, rs -> {
			if (rs.next()) {
				return rs.getInt("id");
			}
			else {
				return null;
			}
		});
		System.out.println("读取到的最新id: " + id);
	}
}
