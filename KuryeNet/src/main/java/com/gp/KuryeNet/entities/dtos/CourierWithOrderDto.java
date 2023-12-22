package com.gp.KuryeNet.entities.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourierWithOrderDto {
	
	private int courierId;
	
	private double courierLatitude;
	
	private double courierLongitude;
	
	private int courierStatus;
	
	private int orderStatus;
	
	private int remainingMinutes;
	
	private Date estimatedDeliveryTime; 
	

}
