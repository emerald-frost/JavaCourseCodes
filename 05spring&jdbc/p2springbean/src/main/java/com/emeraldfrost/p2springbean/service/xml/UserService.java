package com.emeraldfrost.p2springbean.service.xml;

import com.emeraldfrost.p2springbean.entity.User;
import com.emeraldfrost.p2springbean.service.anno.MessageService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserService {

    /**
     * 使用注解注入
     */
    @Autowired
    private MessageService messageService;

    /**
     * 使用xml注入
     */
    private OrderService orderService;

    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    public void listUserOrder(User user) {
        orderService.listOrder(user);
    }

    public void sendMessage(User fromUser, User toUser) {
        messageService.send(fromUser, toUser);
    }
}
