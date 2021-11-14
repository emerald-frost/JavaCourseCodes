package com.emeraldfrost.tccdemoorder.service;

import com.emeraldfrost.tccdemoorder.entity.Order;

public interface IPaymentService {

	void pay(Order order);
}
