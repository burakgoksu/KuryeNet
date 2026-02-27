package com.gp.KuryeNet.entities.concretes;

import java.util.*;
import java.text.*;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gp.KuryeNet.core.entities.AuditableEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "orders")
@SQLDelete(sql = "UPDATE orders SET deleted = true, deleted_at = now(), deleted_by = current_user WHERE order_id = ?")
@SQLRestriction("deleted = false")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","customerBasket"})
public class Order extends AuditableEntity {
	
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
	
	@Column(name="delivery_minutes_ai")
	private int deliveryMinutesAI;
	
	@Column(name="time_taken")
	private int timeTaken;
	
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
	
	@OneToMany(mappedBy="order", fetch = FetchType.EAGER)
	private List<CustomerBasket> customerBasket;
	
	
}
