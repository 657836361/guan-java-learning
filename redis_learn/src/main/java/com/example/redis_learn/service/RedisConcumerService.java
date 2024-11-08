package com.example.redis_learn.service;

/**
 * @author GG
 * @date 2022/4/13
 */
public interface RedisConcumerService {

    /**
     * 消费消息
     *
     * @param message
     */
    void receiveMessage(String message);
}
