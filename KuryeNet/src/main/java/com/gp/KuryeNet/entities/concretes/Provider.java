package com.gp.KuryeNet.entities.concretes;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="providers")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","orders"})
public class Provider {
	
	@Id
	@Column(name="provider_id")
	private int providerId;
	
//	@Column(name="address_id")
//	private int addressId;
	
	@Column(name="provider_type")
	private String providerType;
	
	@Column(name="provider_name")
	private String providerName;
	
	@Column(name="latitude")
	private double providerLatitude;
	
	@Column(name="longitude")
	private double providerLongitude;
	
	@ManyToOne
	@JoinColumn(name="address_id")
	private Address providerAddress;
	
	@OneToMany(mappedBy="provider")
	private List<Order> orders;

}
