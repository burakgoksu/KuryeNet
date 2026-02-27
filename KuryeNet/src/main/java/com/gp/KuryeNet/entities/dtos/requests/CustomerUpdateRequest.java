package com.gp.KuryeNet.entities.dtos.requests;

import java.util.Date;

import jakarta.validation.Valid;

import com.gp.KuryeNet.entities.concretes.Customer;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerUpdateRequest {

    @Schema(example = "Ali")
    private String customerName;
    @Schema(example = "Demir")
    private String customerSurname;
    @Schema(example = "alidemir1@gmail.com")
    private String customerEmail;
    @Schema(example = "2000-01-01T00:00:00.000Z")
    private Date customerBirthday;
    @Schema(example = "0")
    private Double customerLatitude;
    @Schema(example = "0")
    private Double customerLongitude;

    @Valid
    private AddressRequest customerAddress;

    public Customer toEntity() {
        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        customer.setCustomerSurname(customerSurname);
        customer.setCustomerEmail(customerEmail);
        customer.setCustomerBirthday(customerBirthday);
        if (customerLatitude != null) {
            customer.setCustomerLatitude(customerLatitude);
        }
        if (customerLongitude != null) {
            customer.setCustomerLongitude(customerLongitude);
        }
        if (customerAddress != null) {
            customer.setCustomerAddress(customerAddress.toEntity());
        }
        return customer;
    }
}
