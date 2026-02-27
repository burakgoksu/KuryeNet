package com.gp.KuryeNet.entities.concretes;


import java.util.List;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gp.KuryeNet.core.entities.AuditableEntity;
import lombok.*;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name="addresses")
@SQLDelete(sql = "UPDATE addresses SET deleted = true, deleted_at = now(), deleted_by = current_user WHERE address_id = ?")
@SQLRestriction("deleted = false")
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","orders","couriers","providers","customers"})


public class Address extends AuditableEntity {
	
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
