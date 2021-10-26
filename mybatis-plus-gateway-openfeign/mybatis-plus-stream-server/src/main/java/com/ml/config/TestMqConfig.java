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
 * test mq config
 *
 * @author dml
 * @date 2021/10/26 10:33
 */
@Configuration
@RefreshScope
public class TestMqConfig {

    @Value("${test.lazy.exchange:}")
    public String testLazyExchange;
    @Value("${test.lazy.queue:}")
    public String testLazyQueue;
    @Value("${test.lazy.key:}")
    public String testLazyKey;

    @Bean
    public TopicExchange topicTestLazyExchange() {
        TopicExchange exchange = new TopicExchange(testLazyExchange, true, false);
        exchange.setDelayed(true);
        return exchange;
    }

    @Bean
    public Queue queueTestLazyQueue() {
        return new Queue(testLazyQueue, true);
    }

    @Bean
    public Binding testLazyBinding() {
        return BindingBuilder.bind(queueTestLazyQueue()).to(topicTestLazyExchange())
                .with(testLazyKey);
    }

}
