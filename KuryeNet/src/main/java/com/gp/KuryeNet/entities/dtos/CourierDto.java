package com.gp.KuryeNet.entities.dtos;

import java.time.LocalDate;
import java.util.Date;

import com.gp.KuryeNet.entities.concretes.Courier;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierDto {

    private int courierId;
    private Date courierBirthday;
    private String courierName;
    private String courierSurname;
    private String courierIdentityNumber;
    private double courierLatitude;
    private double courierLongitude;
    private String courierEmail;
    private int courierStatus;
    private int dailyShipped;
    private int totalShipped;
    private LocalDate lastDailyUpdate;
    private VehicleDto vehicle;
    private AddressDto courierAddress;

    public static CourierDto fromEntity(Courier courier) {
        if (courier == null) {
            return null;
        }
        VehicleDto vehicleDto = courier.getVehicle() != null
                ? VehicleDto.fromEntity(courier.getVehicle())
                : null;
        AddressDto addressDto = courier.getCourierAddress() != null
                ? AddressDto.fromEntity(courier.getCourierAddress())
                : null;
        return new CourierDto(
                courier.getCourierId(),
                courier.getCourierBirthday(),
                courier.getCourierName(),
                courier.getCourierSurname(),
                courier.getCourierIdentityNumber(),
                courier.getCourierLatitude(),
                courier.getCourierLongitude(),
                courier.getCourierEmail(),
                courier.getCourierStatus(),
                courier.getDaily_shipped(),
                courier.getTotal_shipped(),
                courier.getLastDailyUpdate(),
                vehicleDto,
                addressDto
        );
    }
}
