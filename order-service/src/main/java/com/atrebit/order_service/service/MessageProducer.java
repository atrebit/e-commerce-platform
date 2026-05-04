package com.atrebit.order_service.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageProducer {

    private final AmqpTemplate amqpTemplate;

    @Value("${application.order-exchange}")
    private String orderExchange;

    public void send(String message) {
        amqpTemplate.convertAndSend(orderExchange, "orders.created", message);
    }
    
}