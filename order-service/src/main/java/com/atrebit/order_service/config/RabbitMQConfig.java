package com.atrebit.order_service.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableRabbit
@Configuration
public class RabbitMQConfig {

    @Value("${application.order-exchange}")
    public String exchange;

    @Bean
    public DirectExchange orderExchange() {
        return new DirectExchange(exchange);
    }

}