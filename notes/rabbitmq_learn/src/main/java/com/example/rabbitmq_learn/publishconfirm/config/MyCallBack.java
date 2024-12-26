package com.example.rabbitmq_learn.publishconfirm.config;

import com.alibaba.fastjson.JSON;
import com.example.rabbitmq_learn.delay_plugin.config.CustomDelayedMqConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author GG
 * @date 2022/4/13
 */
@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    /**
     * @param correlationData
     * @param ack             ack==true 说明是正常到了交换机 ack==false 说明交换机不达
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        // 是否交换机可达
        if (!ack) {
            if (correlationData != null) {
                log.info("id={},returnMessage={}", correlationData.getId(), JSON.toJSONString(correlationData.getReturned()));
            }
            log.info("交换机不可达可达");
            // todo 写业务
        }
    }

    /**
     * 请注意!如果你使用了延迟队列插件，那么一定会调用该callback方法，
     * 因为数据并没有提交上去，而是提交在交换器中，过期时间到了才提交上去，
     * 并非是bug！你可以用if进行判断交换机名称来捕捉该报错
     *
     * @param returned
     */
    @Override
    public void returnedMessage(ReturnedMessage returned) {
        //如果配置了发送回调ReturnCallback，插件延迟队列则会回调该方法，因为发送方确实没有投递到队列上，只是在交换器上暂存，等过期时间到了 才会发往队列。
        //并非是BUG，而是有原因的，建议利用if 去拦截这个异常，判断延迟队列交换机名称，然后break;
        if (Objects.equals(returned.getExchange(), CustomDelayedMqConfig.DELAYED_EXCHANGE_NAME)) {
            return;
        }
        log.info("路由不可达,returnMessage={}", JSON.toJSONString(returned));
        // todo 写业务
    }
}
