package com.gp.KuryeNet.entities.concretes;


import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name="addresses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","orders","couriers","providers","customers"})


public class Address {
	
	@Id
	@NotNull
	@Column(name="address_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int addressId;
	
	@NotNull
	@NotBlank
	@Column(name="address")
	private String address;
	
	@NotNull
	@NotBlank
	@Column(name="addres_title")
	private String addressTitle;
	
	@NotNull
	@NotBlank
	@Column(name="city")
	private String city;
	
	@NotNull
	@NotBlank
	@Column(name="district")
	private String district;
	
	@NotNull
	@NotBlank
	@Column(name="street")
	private String street;	
	
	@Column(name="building_number")
	private String buildingNumber;
	
	@Column(name="floor_number")
	private String floorNumber;
	
	@Column(name="apartment_number")
	private String apartmentNumber;
	
	@NotNull
	@NotBlank
	@Column(name="phone_number",unique = true)
	private String phoneNumber;
	
	@OneToMany(mappedBy="orderAddress")
	private List<Order> orders;
	
	@OneToMany(mappedBy="courierAddress")
	private List<Courier> couriers;
	
	@OneToMany(mappedBy="providerAddress")
	private List<Provider> providers;
	
	@OneToMany(mappedBy="customerAddress")
	private List<Customer> customers;
	
//	@JsonIgnore
//	@ManyToMany()
//	@JoinColumn(name="customer_id")
//	private List<Customer> customerss;
	

//  @ManyToMany(mappedBy = "customerAddresses")
//  @ToString.Include
//  @JsonIgnore
//  private List<Customer> customerAddresses;

}
