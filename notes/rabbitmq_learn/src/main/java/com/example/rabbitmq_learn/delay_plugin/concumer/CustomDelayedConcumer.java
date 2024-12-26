package com.example.rabbitmq_learn.delay_plugin.concumer;

import com.example.rabbitmq_learn.delay_plugin.config.CustomDelayedMqConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

/**
 * @author GG
 * @date 2022/4/13
 */
@Component
@Slf4j
public class CustomDelayedConcumer {

    @RabbitListener(queues = CustomDelayedMqConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayedQueue(Message message, Channel channel) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到延时队列的消息：{}", new Date(), msg);
    }
}
