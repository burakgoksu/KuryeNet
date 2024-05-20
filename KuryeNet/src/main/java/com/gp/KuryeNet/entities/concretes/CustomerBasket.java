package com.gp.KuryeNet.entities.concretes;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name="customers_baskets")

public class CustomerBasket {
	
	@Id
	@Column(name="customer_basket_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int customerBasketId;
	
	/*
	@Column(name="order_id")
	private int orderId;
	
	@Column(name="customer_id")
	private int customerId;
	*/ 
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="order_id",referencedColumnName="order_id")
	private Order order;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="customer_id",referencedColumnName="customer_id")
	private Customer customer;

}
