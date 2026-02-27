package com.gp.KuryeNet.core.messaging;

import java.time.Instant;

public class OrderEvent {

    private int orderId;
    private String orderNumber;
    private String eventType;
    private Instant createdAt;

    public OrderEvent() {
    }

    public OrderEvent(int orderId, String orderNumber, String eventType, Instant createdAt) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.eventType = eventType;
        this.createdAt = createdAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
