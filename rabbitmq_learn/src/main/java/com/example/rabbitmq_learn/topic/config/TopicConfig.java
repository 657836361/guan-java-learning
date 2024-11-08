package com.example.rabbitmq_learn.topic.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author GG
 * @date 2022/4/28
 */
@Configuration
public class TopicConfig {
    public static final String NORMAL_TOPIC_EXCHANGE = "normal_topic_exchange";
    public static final String NORMAL_TOPIC_QUEUE1 = "normal_topic_queue1";
    public static final String NORMAL_TOPIC_QUEUE2 = "normal_topic_queue2";


    @Bean(NORMAL_TOPIC_QUEUE1)
    public Queue queue1() {
        return QueueBuilder.durable(NORMAL_TOPIC_QUEUE1).build();
    }

    @Bean(NORMAL_TOPIC_QUEUE2)
    public Queue queue2() {
        return QueueBuilder.durable(NORMAL_TOPIC_QUEUE2).build();
    }

    @Bean(NORMAL_TOPIC_EXCHANGE)
    public TopicExchange normalExchange() {
        return ExchangeBuilder.topicExchange(NORMAL_TOPIC_EXCHANGE).build();
    }

    @Bean
    public Binding bindingQueue1(@Qualifier(NORMAL_TOPIC_QUEUE1) Queue queue, @Qualifier(NORMAL_TOPIC_EXCHANGE) TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("");
    }

    @Bean
    public Binding bindingQueue2(@Qualifier(NORMAL_TOPIC_QUEUE2) Queue queue, @Qualifier(NORMAL_TOPIC_EXCHANGE) TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("");
    }


}
