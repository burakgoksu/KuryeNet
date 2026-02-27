package com.gp.KuryeNet.entities.dtos.requests;

import java.util.Date;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.gp.KuryeNet.entities.concretes.Customer;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerCreateRequest {

    @NotNull
    @NotBlank
    @Schema(example = "Ali")
    private String customerName;

    @NotNull
    @NotBlank
    @Schema(example = "Demir")
    private String customerSurname;

    @NotNull
    @NotBlank
    @Schema(example = "alidemir1@gmail.com")
    private String customerEmail;

    @NotNull
    @Schema(example = "2000-01-01T00:00:00.000Z")
    private Date customerBirthday;

    @Schema(example = "0")
    private double customerLatitude;
    @Schema(example = "0")
    private double customerLongitude;

    @Valid
    private AddressRequest customerAddress;

    public Customer toEntity() {
        Customer customer = new Customer();
        customer.setCustomerName(customerName);
        customer.setCustomerSurname(customerSurname);
        customer.setCustomerEmail(customerEmail);
        customer.setCustomerBirthday(customerBirthday);
        customer.setCustomerLatitude(customerLatitude);
        customer.setCustomerLongitude(customerLongitude);
        if (customerAddress != null) {
            customer.setCustomerAddress(customerAddress.toEntity());
        }
        return customer;
    }
}
