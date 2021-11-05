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
 * order mq config
 *
 * @author dml
 * @date 2021/11/5 9:03
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
    @Value("${order.add.lazy.exchange:}")
    public String orderAddDelayExchange;
    @Value("${order.add.lazy.queue:}")
    public String orderAddDelayQueue;
    @Value("${order.add.lazy.key:}")
    public String orderAddDelayKey;

    @Bean
    public TopicExchange topicOrderAddExchange() {
        return new TopicExchange(orderAddExchange, true, false);
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

    @Bean
    public TopicExchange topicOrderAddDelayExchange() {
        TopicExchange exchange = new TopicExchange(orderAddDelayExchange, true, false);
        exchange.setDelayed(true);
        return exchange;
    }

    @Bean
    public Queue queueOrderAddDelayQueue() {
        return new Queue(orderAddDelayQueue, true);
    }

    @Bean
    public Binding orderAddDelayBinding() {
        return BindingBuilder.bind(queueOrderAddDelayQueue()).to(topicOrderAddDelayExchange())
                .with(orderAddDelayKey);
    }

}
