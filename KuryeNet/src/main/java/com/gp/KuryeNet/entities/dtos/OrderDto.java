package com.gp.KuryeNet.entities.dtos;

import java.util.Date;

import com.gp.KuryeNet.entities.concretes.Order;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDto {

    private int orderId;
    private String orderNumber;
    private Date orderDate;
    private Date deliveryDate;
    private String orderType;
    private Date estimatedDeliveryTime;
    private int remainingMinutes;
    private int remainingDistance;
    private double latitude;
    private double longitude;
    private int orderStatus;
    private int orderedPlatform;
    private int deliveryMinutesAI;
    private int timeTaken;

    private CourierDto courier;
    private AddressDto orderAddress;
    private ProviderDto provider;
    private CustomerDto customer;

    public static OrderDto fromEntity(Order order) {
        if (order == null) {
            return null;
        }

        return new OrderDto(
                order.getOrderId(),
                order.getOrderNumber(),
                order.getOrderDate(),
                order.getDeliveryDate(),
                order.getOrderType(),
                order.getEstimatedDeliveryTime(),
                order.getRemainingMinutes(),
                order.getRemainingDistance(),
                order.getLatitude(),
                order.getLongitude(),
                order.getOrderStatus(),
                order.getOrderedPlatform(),
                order.getDeliveryMinutesAI(),
                order.getTimeTaken(),
                order.getCourier() != null ? CourierDto.fromEntity(order.getCourier()) : null,
                order.getOrderAddress() != null ? AddressDto.fromEntity(order.getOrderAddress()) : null,
                order.getProvider() != null ? ProviderDto.fromEntity(order.getProvider()) : null,
                order.getCustomer() != null ? CustomerDto.fromEntity(order.getCustomer()) : null
        );
    }
}
