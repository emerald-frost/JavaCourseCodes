package com.emeraldfrost.tccdemoaccount.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("t_account")
public class Account {

	@TableId
	private Long id;

	private Long userId;

	private Integer balance;

	private Integer freezeAmount;
}
