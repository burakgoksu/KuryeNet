package com.gp.KuryeNet.entities.concretes;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customers")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","orders","address","addressTitle","city","district","street","buildingNumber","floorNumber","apartmentNumber","phoneNumber"})
public class Customer {
	
	@Id
	@Column(name="customer_id")
	private int customerId;
	
//	@Column(name="address_id")
//	private int addressId;
	
	@Column(name="name")
	private String customerName;
	
	@Column(name="surname")
	private String customerSurname;
	
	@Column(name="email")
	private String customerEmail;
	
	@Column(name="birthday")
	private Date customerBirthday;
	
	@Column(name="latitude")
	private double customerLatitude;
	
	@Column(name="longitude")
	private double customerLongitude;
	
	@OneToMany(mappedBy="customer")
	private List<Order> orders;
	
	@ManyToOne()
	@JoinColumn(name="address_id")
	private Address customerAddress; 	
	
//	@ManyToMany()
//	@JoinColumn(name="address_id")
//	private List<Address> customerAddresses;
		
//    @ManyToMany
//    @JoinTable(
//        name = "customer_address", // Aracı tablonun adı
//        joinColumns = @JoinColumn(name = "customer_id"),
//        inverseJoinColumns = @JoinColumn(name = "address_id"))
//    private List<Address> customerAddresses;
}
