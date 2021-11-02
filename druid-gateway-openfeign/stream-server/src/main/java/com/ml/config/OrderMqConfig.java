package com.ml.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * OrderMqConfig
 *
 * @author dml
 * @date 2021/11/02 21:36
 */
@Configuration
@RefreshScope
public class OrderMqConfig {

    @Value("${order.add.exchange:}")
    public String orderAddExchange;
    @Value("${order.add.queue:}")
    public String orderAddQueue;
    @Value("${order.add.key:}")
    public String orderAddKey;

    @Bean
    public TopicExchange topicOrderAddExchange() {
        TopicExchange exchange = new TopicExchange(orderAddExchange, true, false);
        exchange.setDelayed(true);
        return exchange;
    }

    @Bean
    public Queue queueOrderAddQueue() {
        return new Queue(orderAddQueue, true);
    }

    @Bean
    public Binding orderAddBinding() {
        return BindingBuilder.bind(queueOrderAddQueue()).to(topicOrderAddExchange())
                .with(orderAddKey);
    }

}
