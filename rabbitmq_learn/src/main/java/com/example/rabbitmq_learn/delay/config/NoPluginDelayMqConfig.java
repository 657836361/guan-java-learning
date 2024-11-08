package com.example.rabbitmq_learn.delay.config;

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
public class NoPluginDelayMqConfig {

    public static final String NO_PLUGIN_NORMAL_QUEUE = "no_plugin_normal_queue";
    public static final String NO_PLUGIN_NORMAL_EXCHANGE = "no_plugin_normal_exchange";
    public static final String NO_PLUGIN_DEAD_LETTER_QUEUE = "no_plugin_dead_letter_queue";
    public static final String NO_PLUGIN_DEAD_LETTER_EXCHANGE = "no_plugin_dead_letter_exchange";


    @Bean(NO_PLUGIN_NORMAL_QUEUE)
    public Queue NO_PLUGIN_NORMAL_QUEUE() {
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", NO_PLUGIN_DEAD_LETTER_EXCHANGE);
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", NO_PLUGIN_DEAD_LETTER_QUEUE);
        return QueueBuilder.durable(NO_PLUGIN_NORMAL_QUEUE).withArguments(args).build();
    }

    @Bean(NO_PLUGIN_NORMAL_EXCHANGE)
    public DirectExchange directExchange() {
        return ExchangeBuilder.directExchange(NO_PLUGIN_NORMAL_EXCHANGE).build();
    }


    @Bean(NO_PLUGIN_DEAD_LETTER_QUEUE)
    public Queue NO_PLUGIN_DEAD_LETTER_QUEUE() {
        return QueueBuilder.durable(NO_PLUGIN_DEAD_LETTER_QUEUE).build();
    }

    @Bean(NO_PLUGIN_DEAD_LETTER_EXCHANGE)
    public DirectExchange NO_PLUGIN_DEAD_LETTER_EXCHANGE() {
        return ExchangeBuilder.directExchange(NO_PLUGIN_DEAD_LETTER_EXCHANGE).build();
    }

    @Bean
    public Binding binding1(@Qualifier(NO_PLUGIN_NORMAL_QUEUE) Queue queue,
                            @Qualifier(NO_PLUGIN_NORMAL_EXCHANGE) DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(NO_PLUGIN_NORMAL_QUEUE);
    }

    @Bean
    public Binding binding2(@Qualifier(NO_PLUGIN_DEAD_LETTER_QUEUE) Queue queue,
                            @Qualifier(NO_PLUGIN_DEAD_LETTER_EXCHANGE) DirectExchange directExchange) {
        return BindingBuilder.bind(queue).to(directExchange).with(NO_PLUGIN_DEAD_LETTER_QUEUE);
    }


}
