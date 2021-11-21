package com.emeraldfrost.forexuser.service.impl;

import com.emeraldfrost.forexuser.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
@DubboService
public class UserServiceImpl implements IUserService {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public Long findUserIdByUsername(String username) {
		return jdbcTemplate.queryForObject(
				"select user_id from t_user where username = ?",
				Long.class,
				username
		);
	}
}
