package com.emeraldfrost.tccdemoaccount.mapper;

import com.emeraldfrost.tccdemocommon.model.AccountDTO;
import org.apache.ibatis.annotations.Update;

public interface AccountMapper {

	@Update("update t_account set balance = balance - #{amount}," +
			" freeze_amount = freeze_amount + #{amount} ,update_time = now()" +
			" where user_id = #{userId}  and  balance >= #{amount}  ")
	int update(AccountDTO accountDTO);

	@Update("update t_account set " +
			" freeze_amount = freeze_amount - #{amount}" +
			" where user_id = #{userId}  and freeze_amount >= #{amount} ")
	int confirm(AccountDTO accountDTO);

	@Update("update t_account set balance = balance + #{amount}," +
			" freeze_amount = freeze_amount -  #{amount} " +
			" where user_id = #{userId}  and freeze_amount >= #{amount}")
	int cancel(AccountDTO accountDTO);
}
