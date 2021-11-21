package com.emeraldfrost.forexcny.mapper;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

public interface CnyAccountMapper {

	/**
	 * 余额转移到冻结金额
	 */
	@Update("update t_cny_account " +
			"set balance = balance - #{amount}, frozen_balance = frozen_balance + #{amount} " +
			"where user_id = #{userId} and balance >= #{amount}")
	int normalToFrozen(
			@Param("userId") Long userId,
			@Param("amount") BigDecimal amount
	);

	/**
	 * 冻结金额转移到余额
	 */
	@Update("update t_cny_account " +
			"set balance = balance + #{amount}, frozen_balance = frozen_balance - #{amount} " +
			"where user_id = #{userId} and frozen_balance >= #{amount}")
	int frozenToNormal(
			@Param("userId") Long userId,
			@Param("amount") BigDecimal amount
	);

	/**
	 * 增加到冻结金额
	 */
	@Update("update t_cny_account " +
			"set frozen_balance = frozen_balance + #{amount} " +
			"where user_id = #{userId}")
	int addFrozen(
			@Param("userId") Long userId,
			@Param("amount") BigDecimal amount
	);

	/**
	 * 从冻结金额中扣除
	 */
	@Update("update t_cny_account " +
			"set frozen_balance = frozen_balance - #{amount} " +
			"where user_id = #{userId} and frozen_balance >= #{amount}")
	int subFrozen(
			@Param("userId") Long userId,
			@Param("amount") BigDecimal amount
	);
}
