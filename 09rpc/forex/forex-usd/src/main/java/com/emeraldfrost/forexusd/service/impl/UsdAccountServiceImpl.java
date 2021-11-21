package com.emeraldfrost.forexusd.service.impl;

import java.math.BigDecimal;

import com.emeraldfrost.forexaccount.model.ExchangeRequestDTO;
import com.emeraldfrost.forexaccount.service.ICnyAccountService;
import com.emeraldfrost.forexaccount.service.IUsdAccountService;
import com.emeraldfrost.forexcommon.service.IExchangeRateService;
import com.emeraldfrost.forexusd.mapper.UsdAccountMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;
import org.apache.dubbo.config.annotation.DubboService;
import org.dromara.hmily.annotation.HmilyTCC;

import org.springframework.beans.factory.annotation.Autowired;

@Slf4j
@RequiredArgsConstructor
@DubboService
public class UsdAccountServiceImpl implements IUsdAccountService {

	@Autowired
	private UsdAccountMapper usdAccountMapper;

	@DubboReference
	private ICnyAccountService cnyAccountService;

	@DubboReference
	private IExchangeRateService exchangeRateService;

	@HmilyTCC(confirmMethod = "exchangeInConfirm", cancelMethod = "exchangeInCancel")
	@Override
	public Boolean exchangeIn(Long userId, ExchangeRequestDTO dto) {
		// 先转到冻结金额中
		final int updated = usdAccountMapper.addFrozen(userId, destAmount(dto));
		log.info("usd in, userId: {}, dto: {}", userId, dto);
		if (updated > 0) {
			return Boolean.TRUE;
		}
		else {
			throw new RuntimeException("兑换美元失败");
		}
	}

	/**
	 * 转换后的金额
	 */
	private BigDecimal destAmount(ExchangeRequestDTO dto) {
		// 汇率
		final BigDecimal rate = exchangeRateService.findRate(dto.getSourceCurrency(), dto.getDestCurrency());
		// 转换后的金额
		return dto.getAmount().multiply(rate);
	}

	public Boolean exchangeInConfirm(Long userId, ExchangeRequestDTO dto) {
		log.info("usd in confirm, userId: {}, dto: {}", userId, dto);
		return usdAccountMapper.frozenToNormal(userId, destAmount(dto)) > 0;
	}

	public Boolean exchangeInCancel(Long userId, ExchangeRequestDTO dto) {
		log.error("usd in cancel, userId: {}, dto: {}", userId, dto);
		return usdAccountMapper.subFrozen(userId, destAmount(dto))>0;
	}

	@HmilyTCC(confirmMethod = "exchangeOutConfirm", cancelMethod = "exchangeOutCancel")
	@Override
	public Boolean exchangeOut(Long userId, ExchangeRequestDTO dto) {
		final int updated = usdAccountMapper.normalToFrozen(userId, dto.getAmount());
		log.info("usd out, userId: {}, dto: {}", userId, dto);
		if (updated > 0) {
			cnyAccountService.exchangeIn(userId, dto);
			return Boolean.TRUE;
		}
		else {
			throw new RuntimeException("余额不足");
		}
	}

	public Boolean exchangeOutConfirm(Long userId, ExchangeRequestDTO dto) {
		log.info("usd out confirm, userId: {}, dto: {}", userId, dto);
		final int updated = usdAccountMapper.subFrozen(userId, dto.getAmount());
		return updated > 0;
	}

	public Boolean exchangeOutCancel(Long userId, ExchangeRequestDTO dto) {
		log.error("usd out cancel, userId: {}, dto: {}", userId, dto);
		final int updated = usdAccountMapper.frozenToNormal(userId, dto.getAmount());
		return updated > 0;
	}
}
