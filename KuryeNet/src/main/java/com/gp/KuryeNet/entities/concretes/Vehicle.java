package com.gp.KuryeNet.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="vehicles")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","couriers"})
public class Vehicle {
	
	@Id
	@Column(name="vehicle_id")
	private int vehicleId;
	
	@NotNull
	@Column(name="vehicle_year")
	private int vehicleYear;
	
	@NotNull
	@Column(name="vehicle_type")
	private String vehicleType;
	
	@NotNull
	@Column(name="vehicle_brand")
	private String vehicleBrand;
	
	@NotNull
	@Column(name="vehicle_model")
	private String vehicleModel;
	
	@NotNull
	@Column(name="vehicle_emission")
	private String vehicleEmission;
	
	@NotNull
	@Column(name="vehicle_plate")
	private String vehiclePlate;
	
	@OneToMany(mappedBy = "vehicle")
	private List<Courier> couriers;
	
}
