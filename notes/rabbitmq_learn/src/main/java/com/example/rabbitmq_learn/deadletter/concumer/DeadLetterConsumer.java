package com.example.rabbitmq_learn.deadletter.concumer;

import com.example.rabbitmq_learn.deadletter.config.DeadLetterMqConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author GG
 * @date 2022/4/13
 */
@Component
@Slf4j
public class DeadLetterConsumer {

    @RabbitListener(queues = DeadLetterMqConfig.QUEUE_D)
    public void consume(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("当前时间：{},收到死信队列信息{}", new Date(), msg);
    }
}
