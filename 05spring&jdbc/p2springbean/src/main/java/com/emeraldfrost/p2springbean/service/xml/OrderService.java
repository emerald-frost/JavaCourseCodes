package com.emeraldfrost.p2springbean.service.xml;

import com.emeraldfrost.p2springbean.entity.User;

public class OrderService {

    public void listOrder(User user) {
        System.out.println("list orders of " + user.getUsername());
    }
}
