package com.gp.KuryeNet.entities.concretes;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="couriers")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","orders"})
public class Courier {
	
	@Id
	@Column(name="courier_id")
	private int courierId;
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Istanbul")
	@Column(name="birthday")
	private Date courierBirthday;
	
	@Column(name="vehicle_id")
	private int courierVehicleId;
	
	@Column(name="address_id")
	private int courierAddressId;
	
	@Column(name="name")
	private String courierName;
	
	@Column(name="surname")
	private String courierSurname;
	
	@Column(name="identity_number")
	private String courierIdentityNumber;
	
	@Column(name="latitude")
	private double courierLatitude;
	
	@Column(name="longitude")
	private double courierLongitude;
	
	@Column(name="email")
	private String courierEmail;
	
	@OneToMany(mappedBy = "courier")
	private List<Order> orders;
}
