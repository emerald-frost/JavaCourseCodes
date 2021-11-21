package com.emeraldfrost.forexds.config;

import com.emeraldfrost.forexaccount.model.ExchangeRequestDTO;
import com.emeraldfrost.forexds.constants.DB;
import com.emeraldfrost.forexds.core.DatasourceKeyHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Aspect
@Order(1)
@Component
public class DynamicDataSourceAspect {

	@Pointcut("target(com.emeraldfrost.forexaccount.service.IAccountService)" +
			" && execution(* exchange*(..))")
	public void exchangeMethod() {
	}

	@Before("exchangeMethod()")
	public void before(JoinPoint point) {
		for (Object arg : point.getArgs()) {
			if (arg instanceof ExchangeRequestDTO) {
				ExchangeRequestDTO dto = (ExchangeRequestDTO) arg;
				final String username = dto.getUsername();
				if ("B".equals(username)) {
					DatasourceKeyHolder.set(DB.B);
					log.info("切换数据源到B库");
				}
				else {
					DatasourceKeyHolder.set(DB.A);
					log.info("默认使用A库");
				}
				break;
			}
		}
	}

	@After("exchangeMethod()")
	public void after() {
		DatasourceKeyHolder.remove();
	}
}
