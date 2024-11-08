package com.example.redis_learn.service.impl;

import com.example.redis_learn.service.RedisConcumerService;
import org.springframework.stereotype.Component;

/**
 * @author GG
 * @date 2022/4/13
 */
@Component
public class ChatRedisConcumer implements RedisConcumerService {

    @Override
    public void receiveMessage(String message) {
        System.out.println("收到消息：" + message);
    }
}
