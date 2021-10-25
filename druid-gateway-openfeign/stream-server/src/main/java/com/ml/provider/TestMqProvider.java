package com.ml.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * test mq provider
 *
 * @author dml
 * @date 2021/10/25 15:32
 */
@Slf4j
@Component
public class TestMqProvider implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Value("${test.lazy.exchange:}")
    public String testLazyExchange;
    @Value("${test.lazy.key:}")
    public String testLazyKey;
    @Value("${test.lazy.delay:}")
    public Integer testLazyDelayTime;

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        String id = correlationData != null ? correlationData.getId() : "";
        if (b) {
            log.info("confirm rabbitmq message success, id:{}", id);
        } else {
            log.error("confirm rabbitmq message not success, id:{}, cause:{}", id, s);
        }
    }

    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("returned rabbitmq message。msg:{}, replyCode:{}. replyText:{}, exchange:{}, routingKey :{}",
                returnedMessage.getMessage(), returnedMessage.getReplyCode(), returnedMessage.getReplyText(),
                returnedMessage.getExchange(), returnedMessage.getRoutingKey());
    }

    /**
     * send delay message
     *
     * @param content   content
     */
    public void sendDelayMessage(String content) {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(testLazyExchange, testLazyKey, content, message -> {
                    message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
                    message.getMessageProperties().setDelay(testLazyDelayTime);
                    return message;
                }, correlationData);
        log.info("send delay message success. delay time: {}", testLazyDelayTime);
    }
}
