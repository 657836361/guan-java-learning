package com.example.rabbitmq_learn.deadletter.controller;

import com.example.rabbitmq_learn.deadletter.config.DeadLetterMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * @author GG
 * @date 2022/4/12
 * 死信队列 触发条件
 * 消息 TTL 过期
 * 队列达到最大长度(队列满了，无法再添加数据到 mq 中)
 * 消息被拒绝(basic.reject 或 basic.nack)并且 requeue=false
 */
@Slf4j
@RestController
@RequestMapping("/deadletter")
public class DeadLetterProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send1/{msg}/{routekey}")
    public void send1(@PathVariable String msg, @PathVariable String routekey) {
        log.info("当前时间：{},发送一条信息给两个 TTL 队列:{}", new Date(), msg);
        rabbitTemplate.convertAndSend(DeadLetterMqConfig.X_EXCHANGE, routekey, msg);
    }

    @GetMapping("/send1/{msg}")
    public void send1(@PathVariable String msg) {
        log.info("当前时间：{},发送一条信息给两个 TTL 队列:{}", new Date(), msg);
        rabbitTemplate.convertAndSend(DeadLetterMqConfig.X_EXCHANGE, DeadLetterMqConfig.ROUTING_KEY_XA, "消息来自 ttl 为 10S 的队列: " + msg);
        rabbitTemplate.convertAndSend(DeadLetterMqConfig.X_EXCHANGE, DeadLetterMqConfig.ROUTING_KEY_XB, "消息来自 ttl 为 30S 的队列: " + msg);

    }

}
