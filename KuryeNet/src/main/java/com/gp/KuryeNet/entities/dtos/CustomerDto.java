package com.gp.KuryeNet.entities.dtos;

import java.util.Date;

import com.gp.KuryeNet.entities.concretes.Customer;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerDto {

    private int customerId;
    private String customerName;
    private String customerSurname;
    private String customerEmail;
    private Date customerBirthday;
    private double customerLatitude;
    private double customerLongitude;
    private Integer addressId;

    public static CustomerDto fromEntity(Customer customer) {
        if (customer == null) {
            return null;
        }
        Integer addressId = customer.getCustomerAddress() != null
                ? customer.getCustomerAddress().getAddressId()
                : null;
        return new CustomerDto(
                customer.getCustomerId(),
                customer.getCustomerName(),
                customer.getCustomerSurname(),
                customer.getCustomerEmail(),
                customer.getCustomerBirthday(),
                customer.getCustomerLatitude(),
                customer.getCustomerLongitude(),
                addressId
        );
    }
}
