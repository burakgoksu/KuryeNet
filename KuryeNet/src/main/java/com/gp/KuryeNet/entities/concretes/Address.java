package com.gp.KuryeNet.entities.concretes;


import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","orders","couriers","providers","customers"})

public class Address {
	
	@Id
	@Column(name="address_id")
	private int addressId;
	
	@Column(name="address")
	private String address;
	
	@Column(name="addres_title")
	private String addressTitle;
	
	@Column(name="city")
	private String city;
	
	@Column(name="district")
	private String district;
	
	@Column(name="street")
	private String street;	
	
	@Column(name="building_number")
	private String buildingNumber;
	
	@Column(name="floor_number")
	private String floorNumber;
	
	@Column(name="apartment_number")
	private String apartmentNumber;
	
	@Column(name="phone_number")
	private String phoneNumber;
	
	@OneToMany(mappedBy="orderAddress")
	private List<Order> orders;
	
	@OneToMany(mappedBy="courierAddress")
	private List<Courier> couriers;
	
	@OneToMany(mappedBy="providerAddress")
	private List<Provider> providers;
	
	@OneToMany(mappedBy="customerAddress")
	private List<Customer> customers;
}
