package com.emeraldfrost.tccdemoaccount.service;

import com.emeraldfrost.tccdemocommon.model.AccountDTO;

public interface IAccountService {

	boolean pay(AccountDTO dto);
}
