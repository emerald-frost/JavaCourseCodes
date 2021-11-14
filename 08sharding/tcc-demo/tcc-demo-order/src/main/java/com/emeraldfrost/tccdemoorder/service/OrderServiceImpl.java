package com.emeraldfrost.tccdemoorder.service;

import com.emeraldfrost.tccdemocommon.model.AccountDTO;
import com.emeraldfrost.tccdemocommon.model.OrderDTO;
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
public class OrderServiceImpl implements IOrderService {

	private final IPaymentService paymentService;

	private final OrderMapper orderMapper;

	@Override
	public void createOrder(OrderDTO dto) {
		final Order order = new Order();
		order.setUserId(dto.getUserId());
		order.setAmount(dto.getAmount());
		order.setStatus(0);
		orderMapper.insert(order);
		paymentService.pay(order);
	}
}
