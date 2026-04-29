package com.atrebit.order_service.service;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MessageProducer {

    private final AmqpTemplate amqpTemplate;

    public void send(String message) {
        amqpTemplate.convertAndSend(
            "demo.exchange",
            "demo.key",
            message
        );
        System.out.println("Message sent!");
    }
    
}