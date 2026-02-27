package com.gp.KuryeNet.core.messaging;

import java.time.Instant;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.core.config.properties.MessagingProperties;
import com.gp.KuryeNet.entities.concretes.Order;

@Service
@ConditionalOnProperty(name = "app.messaging.enabled", havingValue = "true")
public class OrderEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final MessagingProperties messagingProperties;

    public OrderEventPublisher(RabbitTemplate rabbitTemplate, MessagingProperties messagingProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.messagingProperties = messagingProperties;
    }

    public void publishOrderCreated(Order order) {
        if (order == null) {
            return;
        }
        OrderEvent event = new OrderEvent(
                order.getOrderId(),
                order.getOrderNumber(),
                "ORDER_CREATED",
                Instant.now()
        );
        rabbitTemplate.convertAndSend(
                messagingProperties.getOrderExchange(),
                messagingProperties.getOrderRoutingKey(),
                event
        );
    }
}
