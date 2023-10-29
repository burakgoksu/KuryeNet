package com.gp.KuryeNet.entities.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderWithCourierDto {

	private int id;
	private String orderNumber;
	private String courierName;
}
