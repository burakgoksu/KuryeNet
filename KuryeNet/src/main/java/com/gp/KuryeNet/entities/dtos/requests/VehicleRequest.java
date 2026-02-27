package com.gp.KuryeNet.entities.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.gp.KuryeNet.entities.concretes.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleRequest {

    @Schema(example = "10")
    private Integer vehicleId;

    @NotNull
    @Schema(example = "2018")
    private Integer vehicleYear;

    @NotNull
    @NotBlank
    @Schema(example = "motor")
    private String vehicleType;

    @NotNull
    @NotBlank
    @Schema(example = "bajaj")
    private String vehicleBrand;

    @NotNull
    @NotBlank
    @Schema(example = "pulsar")
    private String vehicleModel;

    @NotNull
    @NotBlank
    @Schema(example = "benzin")
    private String vehicleEmission;

    @NotNull
    @NotBlank
    @Schema(example = "45TK789")
    private String vehiclePlate;

    public Vehicle toEntity() {
        Vehicle vehicle = new Vehicle();
        if (vehicleId != null) {
            vehicle.setVehicleId(vehicleId);
        }
        if (vehicleYear != null) {
            vehicle.setVehicleYear(vehicleYear);
        }
        vehicle.setVehicleType(vehicleType);
        vehicle.setVehicleBrand(vehicleBrand);
        vehicle.setVehicleModel(vehicleModel);
        vehicle.setVehicleEmission(vehicleEmission);
        vehicle.setVehiclePlate(vehiclePlate);
        return vehicle;
    }
}
