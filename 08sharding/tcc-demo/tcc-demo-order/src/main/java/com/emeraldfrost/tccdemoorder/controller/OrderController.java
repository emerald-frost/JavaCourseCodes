package com.emeraldfrost.tccdemoorder.controller;

import com.emeraldfrost.tccdemocommon.model.OrderDTO;
import com.emeraldfrost.tccdemoorder.service.IOrderService;
import com.emeraldfrost.tccdemoorder.service.OrderServiceImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/order")
public class OrderController {

	private final IOrderService orderService;

	/**
	 * 模拟创建订单并扣款，没有模拟扣减库存的操作
	 */
	@PostMapping("/create-order")
	public String createOrder(@RequestBody OrderDTO dto) {
		orderService.createOrder(dto);
		return "done";
	}
}
