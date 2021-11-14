package com.emeraldfrost.tccdemoaccount.service;

import com.emeraldfrost.tccdemoaccount.mapper.AccountMapper;
import com.emeraldfrost.tccdemocommon.model.AccountDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;

import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AccountServiceImpl implements IAccountService {

	private final AccountMapper accountMapper;

	@HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
	@Override
	public boolean pay(AccountDTO dto) {
		log.info("准备付款");
		return accountMapper.update(dto) > 0;
	}

	public boolean confirm(AccountDTO dto) {
		log.info("执行付款");
		return accountMapper.confirm(dto) > 0;
	}

	public boolean cancel(AccountDTO dto) {
		log.info("取消付款");
		return accountMapper.cancel(dto) > 0;
	}
}
