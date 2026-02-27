package com.gp.KuryeNet.core.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(name = "app.messaging.enabled", havingValue = "true")
public class OrderEventListener {

    private static final Logger logger = LoggerFactory.getLogger(OrderEventListener.class);

    @RabbitListener(queues = "${app.messaging.order-queue}")
    public void onOrderEvent(OrderEvent event) {
        if (event == null) {
            return;
        }
        logger.info("Received order event type={} orderId={} orderNumber={}",
                event.getEventType(), event.getOrderId(), event.getOrderNumber());
    }
}
