package com.gp.KuryeNet.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AIModelPredictionRequest {
	
	private int Delivery_Person_Age;
	private String Weather_Condition;
	private String Road_Traffic_Density;
	private String Type_Of_Order;
	private String Type_Of_Vehicle;
	private int Multiple_Deliveries;
	private String City;
	private int Distance;
	private String Day_Type;
	private String Time_Category;

}
