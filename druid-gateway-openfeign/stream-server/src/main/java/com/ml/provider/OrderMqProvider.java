package com.ml.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * OrderMqConsumer
 *
 * @author dml
 * @date 2021/11/02 21:43
 */
@Slf4j
@Component
public class OrderMqProvider implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnsCallback {

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Value("${order.add.exchange:}")
    public String orderAddExchange;
    @Value("${order.add.key:}")
    public String orderAddKey;

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
    public void sendMessage(String content) {
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
        rabbitTemplate.convertAndSend(orderAddExchange, orderAddKey, content);
        log.info("send message success.");
    }

}
