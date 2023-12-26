package com.gp.KuryeNet.entities.dtos;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

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
	
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Istanbul")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date deliveryDate;
	
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Istanbul")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	private Date estimatedDeliveryTime; 
	

}
