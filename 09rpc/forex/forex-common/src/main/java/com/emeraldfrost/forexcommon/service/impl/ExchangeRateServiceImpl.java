package com.emeraldfrost.forexcommon.service.impl;

import java.math.BigDecimal;

import com.emeraldfrost.forexcommon.service.IExchangeRateService;
import lombok.RequiredArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;

import org.springframework.jdbc.core.JdbcTemplate;

@RequiredArgsConstructor
@DubboService
public class ExchangeRateServiceImpl implements IExchangeRateService {

	private final JdbcTemplate jdbcTemplate;

	@Override
	public BigDecimal findRate(String source, String dest) {
		return jdbcTemplate.queryForObject(
				"select rate from t_exchange_rate where source = ? and dest = ?",
				BigDecimal.class,
				source,
				dest
		);
	}
}
