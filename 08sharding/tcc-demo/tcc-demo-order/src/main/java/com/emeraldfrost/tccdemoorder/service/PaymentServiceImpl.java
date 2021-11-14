package com.emeraldfrost.tccdemoorder.service;

import com.emeraldfrost.tccdemocommon.model.AccountDTO;
import com.emeraldfrost.tccdemoorder.client.AccountClient;
import com.emeraldfrost.tccdemoorder.entity.Order;
import com.emeraldfrost.tccdemoorder.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;

import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaymentServiceImpl implements IPaymentService{

	private final AccountClient accountClient;

	private final OrderMapper orderMapper;

	@HmilyTCC(confirmMethod = "confirm", cancelMethod = "cancel")
	@Override
	public void pay(Order order) {
		accountClient.pay(new AccountDTO(order.getUserId(), order.getAmount()));
	}

	public void confirm(Order order) {
		order.setStatus(1);
		orderMapper.updateById(order);
		log.info("订单支付成功");
	}

	public void cancel(Order order) {
		order.setStatus(-1);
		orderMapper.updateById(order);
		log.info("订单支付失败");
	}
}
