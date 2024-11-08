package com.example.rabbitmq_learn.backupexchange.concumer;

import com.example.rabbitmq_learn.backupexchange.config.BackupExchangeMqConfig;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author GG
 * @date 2022/4/13
 */
@Component
@Slf4j
public class BackupConsumer {

    @RabbitListener(queues = BackupExchangeMqConfig.BACKUP_QUEUE)
    public void consume(Message message, Channel channel) {
        String msg = new String(message.getBody());
        log.info("{},收到消息={}", this.getClass().getSimpleName(), msg);
    }
}
