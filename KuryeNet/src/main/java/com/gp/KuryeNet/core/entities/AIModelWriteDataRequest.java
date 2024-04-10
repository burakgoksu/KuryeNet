package com.gp.KuryeNet.core.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonPropertyOrder({ "delivery_Person_Age", "order_Date", "time_Ordered","weather_Condition","road_Traffic_Density","type_Of_Order" ,"type_Of_Vehicle","multiple_Deliveries","city","time_Taken","distance","day_Type","time_Category","restaurant_Latitude","restaurant_Longitude","delivery_Location_Latitude","delivery_Location_Longitude"})
public class AIModelWriteDataRequest {

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

	private String Order_Date;

	private String Time_Ordered;

	private long Time_Taken;

	private double Restaurant_Latitude;

	private double Restaurant_Longitude;

	private double Delivery_Location_Latitude;

	private double Delivery_Location_Longitude;

}
