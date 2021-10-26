package com.ml.consumer;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
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
        long deliveryTag = msg.getMessageProperties().getDeliveryTag();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        log.info("test delay queue receive {}, time: {}", new String(msg.getBody()), format.format(new Date()));
        if (Objects.isNull(msg.getBody())) {
            return;
        }
        String message = new String(msg.getBody(), StandardCharsets.UTF_8);
        log.info("test mq consumer message: {}", message);
        channel.basicAck(deliveryTag, true);
    }

}
