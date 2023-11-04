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
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
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
	
	@NotNull
	@NotBlank
	@Column(name="name")
	private String customerName;
	
	@NotNull
	@NotBlank
	@Column(name="surname")
	private String customerSurname;
	
	@NotNull
	@NotBlank
	@Column(name="email",unique = true)
	private String customerEmail;
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Istanbul")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
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
	
//	@ManyToMany
//    @JoinTable(name = "cvs_skills",
//            joinColumns = {@JoinColumn(name = "cv_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "candidate_skill_id", referencedColumnName = "id")})
//    @JsonIgnoreProperties(value = {"candidate"})
//    @ToString.Exclude
//    private List<CandidateSkill> candidateSkills;
}
