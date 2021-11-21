package com.emeraldfrost.forexcny.controller;

import com.emeraldfrost.forexaccount.model.ExchangeRequestDTO;
import com.emeraldfrost.forexaccount.service.ICnyAccountService;
import com.emeraldfrost.forexuser.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.DubboReference;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/account")
public class AccountController {

	@DubboReference
	private IUserService userService;

	@Autowired
	private ICnyAccountService cnyAccountService;

	@PostMapping("/exchange")
	public Boolean exchange(ExchangeRequestDTO dto) {
		final Long userId = userService.findUserIdByUsername(dto.getUsername());
		if ("USD".equals(dto.getDestCurrency())) {
			return cnyAccountService.exchangeOut(userId, dto);
		}
		throw new IllegalArgumentException();
	}
}
