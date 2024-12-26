package com.example.rabbitmq_learn.backupexchange.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author GG
 * @date 2022/4/12
 */
@Slf4j
@RestController
@RequestMapping("/backupExchange")
public class BackupExchangeProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/send/{exchange}/{routekey}/{msg}")
    public void send1(@PathVariable String exchange, @PathVariable String msg, @PathVariable String routekey) {
        log.info("生产者发送一条消息，exchange={},routekey={},msg={}", exchange, routekey, msg);
        rabbitTemplate.convertAndSend(exchange, routekey, msg);
    }
}
