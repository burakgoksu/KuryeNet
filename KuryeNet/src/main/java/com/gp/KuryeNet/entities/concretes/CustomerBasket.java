package com.gp.KuryeNet.entities.concretes;

import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gp.KuryeNet.core.entities.AuditableEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customers_baskets")
@SQLDelete(sql = "UPDATE customers_baskets SET deleted = true, deleted_at = now(), deleted_by = current_user WHERE customer_basket_id = ?")
@SQLRestriction("deleted = false")

public class CustomerBasket extends AuditableEntity {
	
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
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="order_id",referencedColumnName="order_id")
	private Order order;
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="customer_id",referencedColumnName="customer_id")
	private Customer customer;

}
