package com.emeraldfrost.tccdemoaccount.controller;

import com.emeraldfrost.tccdemoaccount.service.IAccountService;
import com.emeraldfrost.tccdemocommon.model.AccountDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/account")
public class AccountController {

	private final IAccountService accountService;

	@PostMapping("/pay")
	public Boolean pay(@RequestBody AccountDTO dto) {
		return accountService.pay(dto);
	}
}
