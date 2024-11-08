package com.example.rabbitmq_learn.publishconfirm.controller;

import com.example.rabbitmq_learn.publishconfirm.config.MyCallBack;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
@RequestMapping("/pubishConfirm")
public class PubishConfirmController implements InitializingBean {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private MyCallBack myCallBack;
    @Value("${enable.rabbitmq.pubilish-confirm}")
    private boolean enableRabbitmqRubilishConfirm;

    /**
     * 测试交换机不达和路由不达造成的问题
     *
     * @param exchange
     * @param msg
     * @param routekey
     */
    @GetMapping("/send/{exchange}/{routekey}/{msg}")
    public void send1(@PathVariable String exchange, @PathVariable String msg, @PathVariable String routekey) {
        log.info("生产者发送一条消息，exchange={},routekey={},msg={}", exchange, routekey, msg);
        rabbitTemplate.convertAndSend(exchange, routekey, msg, new CorrelationData());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (enableRabbitmqRubilishConfirm) {
            rabbitTemplate.setConfirmCallback(myCallBack);
            rabbitTemplate.setReturnsCallback(myCallBack);
        }
    }
}
