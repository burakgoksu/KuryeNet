package com.gp.KuryeNet.entities.dtos;

import com.gp.KuryeNet.entities.concretes.Vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {

    private int vehicleId;
    private int vehicleYear;
    private String vehicleType;
    private String vehicleBrand;
    private String vehicleModel;
    private String vehicleEmission;
    private String vehiclePlate;

    public static VehicleDto fromEntity(Vehicle vehicle) {
        if (vehicle == null) {
            return null;
        }
        return new VehicleDto(
                vehicle.getVehicleId(),
                vehicle.getVehicleYear(),
                vehicle.getVehicleType(),
                vehicle.getVehicleBrand(),
                vehicle.getVehicleModel(),
                vehicle.getVehicleEmission(),
                vehicle.getVehiclePlate()
        );
    }
}
