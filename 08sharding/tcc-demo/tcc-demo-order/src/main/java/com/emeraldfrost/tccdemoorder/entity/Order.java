package com.emeraldfrost.tccdemoorder.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName("t_order")
public class Order {

	@TableId
	private Long orderId;

	private Long userId;

	private Integer amount;

	/**
	 * 0：支付中
	 * 1：支付成功
	 * -1：支付失败
	 */
	private Integer status;
}
