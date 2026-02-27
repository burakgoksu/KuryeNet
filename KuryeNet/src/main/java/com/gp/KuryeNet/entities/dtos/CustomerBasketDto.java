package com.gp.KuryeNet.entities.dtos;

import com.gp.KuryeNet.entities.concretes.CustomerBasket;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBasketDto {

    private int customerBasketId;
    private Integer orderId;
    private Integer customerId;

    public static CustomerBasketDto fromEntity(CustomerBasket customerBasket) {
        if (customerBasket == null) {
            return null;
        }
        Integer orderId = customerBasket.getOrder() != null ? customerBasket.getOrder().getOrderId() : null;
        Integer customerId = customerBasket.getCustomer() != null ? customerBasket.getCustomer().getCustomerId() : null;
        return new CustomerBasketDto(
                customerBasket.getCustomerBasketId(),
                orderId,
                customerId
        );
    }
}
