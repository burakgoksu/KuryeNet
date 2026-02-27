package com.gp.KuryeNet.entities.dtos.requests;

import java.util.Date;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.gp.KuryeNet.entities.concretes.Address;
import com.gp.KuryeNet.entities.concretes.Courier;
import com.gp.KuryeNet.entities.concretes.Customer;
import com.gp.KuryeNet.entities.concretes.Order;
import com.gp.KuryeNet.entities.concretes.Provider;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {

    @NotNull
    @NotBlank
    @Schema(example = "ORD-20240210-0001")
    private String orderNumber;

    @Schema(example = "2024-02-10T12:30:00.000Z")
    private Date orderDate;
    @Schema(example = "2024-02-10T13:10:00.000Z")
    private Date deliveryDate;

    @NotNull
    @NotBlank
    @Schema(example = "yemek")
    private String orderType;

    @NotNull
    @Schema(example = "2024-02-10T13:00:00.000Z")
    private Date estimatedDeliveryTime;

    @Schema(example = "0")
    private int remainingMinutes;
    @Schema(example = "0")
    private int remainingDistance;
    @Schema(example = "0")
    private double latitude;
    @Schema(example = "0")
    private double longitude;
    @Schema(example = "1")
    private int orderStatus;
    @Schema(example = "1")
    private int orderedPlatform;
    @Schema(example = "0")
    private int deliveryMinutesAI;
    @Schema(example = "0")
    private int timeTaken;

    @Valid
    private CourierRef courier;

    @Valid
    private AddressRef orderAddress;

    @Valid
    private ProviderRef provider;

    @Valid
    private CustomerRef customer;

    public Order toEntity() {
        Order order = new Order();
        order.setOrderNumber(orderNumber);
        order.setOrderDate(orderDate);
        order.setDeliveryDate(deliveryDate);
        order.setOrderType(orderType);
        order.setEstimatedDeliveryTime(estimatedDeliveryTime);
        order.setRemainingMinutes(remainingMinutes);
        order.setRemainingDistance(remainingDistance);
        order.setLatitude(latitude);
        order.setLongitude(longitude);
        order.setOrderStatus(orderStatus);
        order.setOrderedPlatform(orderedPlatform);
        order.setDeliveryMinutesAI(deliveryMinutesAI);
        order.setTimeTaken(timeTaken);
        if (courier != null && courier.getCourierId() != null) {
            Courier courierEntity = new Courier();
            courierEntity.setCourierId(courier.getCourierId());
            order.setCourier(courierEntity);
        }
        if (orderAddress != null && orderAddress.getAddressId() != null) {
            Address addressEntity = new Address();
            addressEntity.setAddressId(orderAddress.getAddressId());
            order.setOrderAddress(addressEntity);
        }
        if (provider != null && provider.getProviderId() != null) {
            Provider providerEntity = new Provider();
            providerEntity.setProviderId(provider.getProviderId());
            order.setProvider(providerEntity);
        }
        if (customer != null && customer.getCustomerId() != null) {
            Customer customerEntity = new Customer();
            customerEntity.setCustomerId(customer.getCustomerId());
            order.setCustomer(customerEntity);
        }
        return order;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CourierRef {
        @Schema(example = "10")
        private Integer courierId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class AddressRef {
        @Schema(example = "20")
        private Integer addressId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ProviderRef {
        @Schema(example = "5")
        private Integer providerId;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class CustomerRef {
        @Schema(example = "8")
        private Integer customerId;
    }
}
