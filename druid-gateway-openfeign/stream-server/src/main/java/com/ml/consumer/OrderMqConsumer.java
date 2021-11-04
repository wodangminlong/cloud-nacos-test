package com.ml.consumer;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ml.ApiResponse;
import com.ml.openfeign.OrderFeignClient;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

/**
 * OrderMqConsumer
 *
 * @author dml
 * @date 2021/11/02 21:43
 */
@Slf4j
@Component
public class OrderMqConsumer {

    @Resource
    private OrderFeignClient orderFeignClient;

    @RabbitListener(queues = "${order.add.queue}")
    @RabbitHandler
    public void orderAddDelayQueueMessage(Message msg, Channel channel) throws IOException {
        byte[] msgBody = msg.getBody();
        if (Objects.isNull(msgBody)) {
            return;
        }
        String message = new String(msgBody, StandardCharsets.UTF_8);
        log.info("order mq consumer message: {}", message);
        JSONObject jsonObject = JSON.parseObject(message);
        int type = jsonObject.getIntValue("type");
        String goodId = jsonObject.getString("goodId");
        ApiResponse apiResponse;
        // type 0 add order
        if (type == 0) {
            apiResponse = orderFeignClient.addOrder(jsonObject.getString("orderId"), goodId);
            log.info("orderFeignClient.addOrder result: {}", JSON.toJSONString(apiResponse));
        } else {
            apiResponse = orderFeignClient.addSecKill(goodId);
            log.info("orderFeignClient.addSecKill result: {}", JSON.toJSONString(apiResponse));
        }
        long deliveryTag = msg.getMessageProperties().getDeliveryTag();
        channel.basicAck(deliveryTag, true);
    }

}
