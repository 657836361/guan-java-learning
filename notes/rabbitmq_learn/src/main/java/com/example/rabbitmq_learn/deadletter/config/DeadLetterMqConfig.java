package com.example.rabbitmq_learn.deadletter.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author GG
 * @date 2022/4/13
 */
@Configuration
public class DeadLetterMqConfig {

    public static final String X_EXCHANGE = "x_exchange";
    public static final String Y_DEAD_LETTET_EXCHANGE = "y_dead_lettet_exchange";
    public static final String QUEUE_A = "queue_a";
    public static final String QUEUE_B = "queue_b";
    public static final String QUEUE_D = "queue_d";
    public static final String ROUTING_KEY_XA = "XA";
    public static final String ROUTING_KEY_XB = "XB";
    public static final String ROUTING_KEY_YD = "YD";

    @Bean(QUEUE_A)
    public Queue queueA() {
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", Y_DEAD_LETTET_EXCHANGE);
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", ROUTING_KEY_YD);
        //声明队列的 TTL
        args.put("x-message-ttl", 10000);
        return QueueBuilder.durable(QUEUE_A).withArguments(args).build();
    }

    @Bean(QUEUE_B)
    public Queue queueB() {
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", Y_DEAD_LETTET_EXCHANGE);
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", ROUTING_KEY_YD);
        //声明队列的 TTL
        args.put("x-message-ttl", 30000);
        return QueueBuilder.durable(QUEUE_B).withArguments(args).build();
    }

    @Bean(QUEUE_D)
    public Queue queueD() {
        return QueueBuilder.durable(QUEUE_D).build();
    }

    @Bean(X_EXCHANGE)
    public DirectExchange xExchange() {
        return ExchangeBuilder.directExchange(X_EXCHANGE).build();
    }

    @Bean(Y_DEAD_LETTET_EXCHANGE)
    public DirectExchange yDeadLettetExchange() {
        return ExchangeBuilder.directExchange(Y_DEAD_LETTET_EXCHANGE).build();
    }

    @Bean
    public Binding bindingXExchangeWithQueueA(@Qualifier(QUEUE_A) Queue queueA, @Qualifier(X_EXCHANGE) DirectExchange xExchange) {
        return BindingBuilder.bind(queueA).to(xExchange).with(ROUTING_KEY_XA);
    }

    @Bean
    public Binding bindingXExchangeWithQueueB(@Qualifier(QUEUE_B) Queue queueb, @Qualifier(X_EXCHANGE) DirectExchange xExchange) {
        return BindingBuilder.bind(queueb).to(xExchange).with(ROUTING_KEY_XB);
    }

    @Bean
    public Binding bindingYDeadLetterExchangeWithQueueD(@Qualifier(QUEUE_D) Queue queueD, @Qualifier(Y_DEAD_LETTET_EXCHANGE) DirectExchange yDeadLettetExchange) {
        return BindingBuilder.bind(queueD).to(yDeadLettetExchange).with(ROUTING_KEY_YD);
    }
}
