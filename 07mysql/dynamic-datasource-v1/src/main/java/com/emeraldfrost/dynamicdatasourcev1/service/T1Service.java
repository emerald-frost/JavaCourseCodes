package com.emeraldfrost.dynamicdatasourcev1.service;

import java.util.Collections;
import java.util.Map;

import com.emeraldfrost.dynamicdatasourcev1.core.annotation.DS;
import lombok.RequiredArgsConstructor;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
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
	@DS
	public void modify() {
		final SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("t1")
				.usingGeneratedKeyColumns("id");
		final Map<String, String> param = Collections.singletonMap("some_data", "随便写入点什么" + System.currentTimeMillis());
		final Number newId = simpleJdbcInsert.executeAndReturnKey(param);
		System.out.println("插入记录对应的id: " + newId.intValue());
	}

	/**
	 * 输出最新的id
	 */
	@DS(readOnly = true)
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
