package com.gp.KuryeNet.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierWithVehicleDto {
	
	private int courierId;
	
	private String courierName;
	
	private String courierSurname;
	
	private String vehicleType;
	
	private String vehicleBrand;
	
	private String vehiclePlate;
	
}
