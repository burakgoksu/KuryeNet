package com.gp.KuryeNet.entities.dtos.requests;

import java.time.LocalDate;
import java.util.Date;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.gp.KuryeNet.entities.concretes.Courier;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierCreateRequest {

    @NotNull
    @Schema(example = "2023-11-13T11:57:14.968Z")
    private Date courierBirthday;

    @NotNull
    @NotBlank
    @Schema(example = "Mehmet")
    private String courierName;

    @NotNull
    @NotBlank
    @Schema(example = "Demir")
    private String courierSurname;

    @NotNull
    @NotBlank
    @Schema(example = "55632014512")
    private String courierIdentityNumber;

    @Schema(example = "0")
    private double courierLatitude;
    @Schema(example = "0")
    private double courierLongitude;

    @NotNull
    @NotBlank
    @Schema(example = "mehmetdemir1@gmail.com")
    private String courierEmail;

    @Schema(example = "100")
    private int courierStatus;
    @Schema(example = "0")
    private int dailyShipped;
    @Schema(example = "0")
    private int totalShipped;
    @Schema(example = "2024-02-10")
    private LocalDate lastDailyUpdate;

    @Valid
    private VehicleRequest vehicle;

    @Valid
    private AddressRequest courierAddress;

    public Courier toEntity() {
        Courier courier = new Courier();
        courier.setCourierBirthday(courierBirthday);
        courier.setCourierName(courierName);
        courier.setCourierSurname(courierSurname);
        courier.setCourierIdentityNumber(courierIdentityNumber);
        courier.setCourierLatitude(courierLatitude);
        courier.setCourierLongitude(courierLongitude);
        courier.setCourierEmail(courierEmail);
        courier.setCourierStatus(courierStatus);
        courier.setDaily_shipped(dailyShipped);
        courier.setTotal_shipped(totalShipped);
        courier.setLastDailyUpdate(lastDailyUpdate);
        if (vehicle != null) {
            courier.setVehicle(vehicle.toEntity());
        }
        if (courierAddress != null) {
            courier.setCourierAddress(courierAddress.toEntity());
        }
        return courier;
    }
}
