package com.emeraldfrost.forexcommon.service;

import java.math.BigDecimal;

/**
 * 查找汇率
 */
public interface IExchangeRateService {

	BigDecimal findRate(String source, String dest);
}
