package com.example.rabbitmq_learn.topic.controller;

import com.example.rabbitmq_learn.topic.config.TopicConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 测试topic模式下路由为“”时的情况
 *
 * @author GG
 * @date 2022/4/12
 */
@Slf4j
@RestController
@RequestMapping("/topicExchange")
public class TopicController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send/{msg}")
    public void send1(@PathVariable String msg) {
        log.info("生产者发送一条消息，msg={}", msg);
        rabbitTemplate.convertAndSend(TopicConfig.NORMAL_TOPIC_EXCHANGE, "", msg);
    }
}
