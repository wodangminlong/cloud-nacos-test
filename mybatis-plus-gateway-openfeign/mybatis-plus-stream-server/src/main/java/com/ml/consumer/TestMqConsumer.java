package com.ml.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * test mq consumer
 *
 * @author dml
 * @date 2021/10/26 10:37
 */
@Slf4j
@Component
public class TestMqConsumer {

    @RabbitListener(queues = "${test.lazy.queue}")
    @RabbitHandler
    public void testDelayQueueMessage(Message msg, Channel channel) throws IOException {
        byte[] msgBody = msg.getBody();
        if (Objects.isNull(msgBody)) {
            return;
        }
        String message = new String(msgBody, StandardCharsets.UTF_8);
        log.info("test mq consumer message: {}", message);
        long deliveryTag = msg.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag, true);
    }

}
