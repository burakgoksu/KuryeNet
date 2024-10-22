package com.gp.KuryeNet.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DirectionsResponse {
	
	private int remainingMinutes; 
	
	private String remainingMinutesText;
	
	private int remainingDistance;
	
	private String remainingDistanceText;

}
