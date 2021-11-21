package com.emeraldfrost.forexaccount.service;

import com.emeraldfrost.forexaccount.model.ExchangeRequestDTO;
import org.dromara.hmily.annotation.Hmily;

public interface IAccountService {

	/**
	 * 转入
	 */
	@Hmily
	Boolean exchangeIn(Long userId, ExchangeRequestDTO dto);

	/**
	 * 转出
	 */
	@Hmily
	Boolean exchangeOut(Long userId, ExchangeRequestDTO dto);
}
