package com.atrebit.inventory_service.service;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import com.atrebit.inventory_service.dto.domain.Order;
import lombok.RequiredArgsConstructor;
import tools.jackson.databind.ObjectMapper;

@Service
@RequiredArgsConstructor
public class Consumer{
    private final OdooService odoo;

    @RabbitListener(queues = {"${application.order-queue}"})
    public void consume(String message){
        ObjectMapper mapper = new ObjectMapper();
        Order order = mapper.readValue(message, Order.class);

        odoo.createOrder(order);
    }
}