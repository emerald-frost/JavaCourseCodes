package com.emeraldfrost.p2springbean;

import com.emeraldfrost.p2springbean.entity.User;
import com.emeraldfrost.p2springbean.service.xml.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Application {

    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        UserService userService = context.getBean(UserService.class);

        final User user1 = new User();
        user1.setUsername("aaa");

        final User user2 = new User();
        user2.setUsername("bbb");

        userService.listUserOrder(user1);

        userService.sendMessage(user1, user2);
    }
}
