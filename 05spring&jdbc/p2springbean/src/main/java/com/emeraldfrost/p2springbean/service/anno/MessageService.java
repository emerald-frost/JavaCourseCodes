package com.emeraldfrost.p2springbean.service.anno;

import com.emeraldfrost.p2springbean.entity.User;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public void send(User fromUser, User toUser) {
        final String str = String.format(
                "send message from %s to %s",
                fromUser.getUsername(),
                toUser.getUsername()
        );
        System.out.println(str);
    }
}
