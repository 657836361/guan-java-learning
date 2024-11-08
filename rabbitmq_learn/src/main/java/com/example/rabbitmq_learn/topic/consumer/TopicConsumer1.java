package com.example.rabbitmq_learn.topic.consumer;

import com.example.rabbitmq_learn.topic.config.TopicConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author GG
 * @date 2022/4/13
 */
@Component
@Slf4j
public class TopicConsumer1 {

    @RabbitListener(queues = TopicConfig.NORMAL_TOPIC_QUEUE1)
    public void consume(Message message, Channel channel) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            String msg = new String(message.getBody());
            log.info("{},收到消息={}", this.getClass().getSimpleName(), msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
