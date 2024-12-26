package com.example.rabbitmq_learn.backupexchange.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author GG
 * @date 2022/4/13
 */
@Configuration
public class BackupExchangeMqConfig {

    public static final String CONFIRM_EXCHANGE = "backup_exchange.confirm.exchange";
    public static final String BACKUP_EXCHANGE = "backup_exchange.backup.exchange";
    public static final String CONFIRM_QUEUE = "backup_exchange.confirm.queue";
    public static final String BACKUP_QUEUE = "backup_exchange.backup.queue";
    public static final String WARNING_QUEUE = "backup_exchange.warning.queue";
    public static final String ROUTING_KEY = "key1";

    @Bean(CONFIRM_QUEUE)
    public Queue confirmQueue() {
        return QueueBuilder.durable(CONFIRM_QUEUE).build();
    }

    @Bean(BACKUP_QUEUE)
    public Queue backupQueue() {
        return QueueBuilder.durable(BACKUP_QUEUE).build();
    }

    @Bean(WARNING_QUEUE)
    public Queue warningQueue() {
        return QueueBuilder.durable(WARNING_QUEUE).build();
    }

    @Bean(CONFIRM_EXCHANGE)
    public DirectExchange normalExchange() {
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE).alternate(BACKUP_EXCHANGE).build();
    }

    @Bean(BACKUP_EXCHANGE)
    public FanoutExchange backupExchange() {
        return ExchangeBuilder.fanoutExchange(BACKUP_EXCHANGE).build();
    }

    @Bean
    public Binding bindingConfirmQueue(@Qualifier(CONFIRM_QUEUE) Queue queue, @Qualifier(CONFIRM_EXCHANGE) DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding bindingBackupQueue(@Qualifier(BACKUP_QUEUE) Queue queue, @Qualifier(BACKUP_EXCHANGE) FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }

    @Bean
    public Binding bindingWarningQueue(@Qualifier(WARNING_QUEUE) Queue queue, @Qualifier(BACKUP_EXCHANGE) FanoutExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange);
    }
}
