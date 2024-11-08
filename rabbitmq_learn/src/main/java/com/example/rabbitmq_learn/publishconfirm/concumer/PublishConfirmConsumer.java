package com.example.rabbitmq_learn.publishconfirm.concumer;

import com.example.rabbitmq_learn.publishconfirm.config.PublishConfirmConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;
import java.util.Objects;

/**
 * @author GG
 * @date 2022/4/13
 */
@Component
@Slf4j
public class PublishConfirmConsumer {

    @RabbitListener(queues = PublishConfirmConfig.CONFIRM_QUEUE_NAME)
    public void consume(Message message, Channel channel) {
        String msg = new String(message.getBody());
        if (Objects.equals(msg, "111")) {
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                log.info("当前时间：{},收到信息{}", new Date(), msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                log.info("当前时间：{},收到信息{}", new Date(), msg);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
