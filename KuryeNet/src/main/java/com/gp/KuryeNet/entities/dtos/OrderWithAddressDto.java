package com.gp.KuryeNet.entities.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderWithAddressDto {

	private int id;
	private String orderNumber;
	private int orderedPlatform;
	private int orderStatus;
	private Date orderDate;
	private Date deliveryDate;
	private Date estimatedDeliveryTime;
	private int remainingMinutes;
	private String address;
	private String city;
	private String district;
	private String street;
	private String buildingNumber;
	private String floorNumber;
	private String apartmentNumber;
	private String phoneNumber;

	

	
}
