package com.example.rabbitmq_learn.delay_plugin.controller;

import com.example.rabbitmq_learn.delay_plugin.config.CustomDelayedMqConfig;
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
 */
@Slf4j
@RestController
@RequestMapping("/customDelayed")
public class CustomDelayedProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("sendDelayMsg/{message}/{delayTime}")
    public void sendMsg(@PathVariable String message, @PathVariable Integer delayTime) {
        rabbitTemplate.convertAndSend(CustomDelayedMqConfig.DELAYED_EXCHANGE_NAME, CustomDelayedMqConfig.DELAYED_ROUTING_KEY, message,
                correlationData -> {
                    correlationData.getMessageProperties().setDelay(delayTime);
                    return correlationData;
                }
        );
        log.info(" 当 前 时 间 ： {}, 发 送 一 条 延 迟 {} 毫秒的信息给队列 delayed.queue:{}", new Date(), delayTime, message);
    }

}
