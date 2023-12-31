package com.gp.KuryeNet.entities.concretes;

import java.util.*;
import java.text.*;


import javax.persistence.Column;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Order {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private int orderId;
	
	@NotBlank
	@Column(name="order_number",unique = true)
	private String orderNumber;
	
	//@Column(name="customer_id")
	//private int customerId;
	
	//@Column(name="courier_id")
	//private int courier_id;
	
	//private String dateTimePattern = "yyyy-MM-dd HH:mm:ss";
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Istanbul")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="order_date")
	private Date orderDate;

	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Istanbul")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="delivery_date")
	private Date deliveryDate;
	
	@NotNull
	@NotBlank
	@Column(name="order_type")
	private String orderType;
	
	//@Column(name="address_id")
	//private int addressId;
	
	//@Column(name="provider_id")
	//private int providerId;
	
	@NotNull
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Europe/Istanbul")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Column(name="estimated_delivery_time")
	private Date estimatedDeliveryTime;
	
	@Column(name="remaining_minutes")
	private int remainingMinutes;
	
	@Column(name="remaining_distance")
	private int remainingDistance;
	
	@Column(name="latitude")
	private double latitude;
	
	@Column(name="longitude")
	private double longitude;
	
	@NotNull
	@Column(name="order_status")
	private int orderStatus;
	
	@Column(name="ordered_platform")
	private int orderedPlatform;
	
	@ManyToOne()
	@JoinColumn(name="courier_id")
	private Courier courier;
	
	@ManyToOne()
	@JoinColumn(name="address_id")
	private Address orderAddress;
	
	@ManyToOne()
	@JoinColumn(name="provider_id")
	private Provider provider;
	
	@ManyToOne()
	@JoinColumn(name="customer_id")
	private Customer customer;
	
	
}
