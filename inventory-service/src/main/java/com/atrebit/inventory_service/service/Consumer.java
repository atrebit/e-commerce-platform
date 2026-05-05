package com.atrebit.inventory_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class Consumer{
    @RabbitListener(queues = {"${application.order-queue}"})
    public void consume(String message){
        log.info(String.format("sending notification with content: %s", message));
    }
}