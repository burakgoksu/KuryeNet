package com.gp.KuryeNet.core.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import jakarta.validation.constraints.NotBlank;

@Validated
@ConfigurationProperties(prefix = "app.messaging")
public class MessagingProperties {

    private boolean enabled = false;

    @NotBlank
    private String orderExchange = "order.exchange";

    @NotBlank
    private String orderQueue = "order.events";

    @NotBlank
    private String orderRoutingKey = "order.events";

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getOrderExchange() {
        return orderExchange;
    }

    public void setOrderExchange(String orderExchange) {
        this.orderExchange = orderExchange;
    }

    public String getOrderQueue() {
        return orderQueue;
    }

    public void setOrderQueue(String orderQueue) {
        this.orderQueue = orderQueue;
    }

    public String getOrderRoutingKey() {
        return orderRoutingKey;
    }

    public void setOrderRoutingKey(String orderRoutingKey) {
        this.orderRoutingKey = orderRoutingKey;
    }
}
