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
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gp.KuryeNet.core.entities.UserRole;
import com.gp.KuryeNet.core.entities.AuditableEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="customers")
@SQLDelete(sql = "UPDATE customers SET deleted = true, deleted_at = now(), deleted_by = current_user WHERE customer_id = ?")
@SQLRestriction("deleted = false")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","orders","address","addressTitle","city","district","street","buildingNumber","floorNumber","apartmentNumber","phoneNumber","customerBasket"})
public class Customer extends AuditableEntity {
	
	@Id
	@Column(name="customer_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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
	
	@ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
	@JoinColumn(name="address_id")
	private Address customerAddress; 	
	
	@OneToMany(mappedBy="customer", fetch = FetchType.EAGER)
	private List<CustomerBasket> customerBasket;
	
//	@ManyToMany
//    @JoinTable(name = "cvs_skills",
//            joinColumns = {@JoinColumn(name = "cv_id", referencedColumnName = "id")},
//            inverseJoinColumns = {@JoinColumn(name = "candidate_skill_id", referencedColumnName = "id")})
//    @JsonIgnoreProperties(value = {"candidate"})
//    @ToString.Exclude
//    private List<CandidateSkill> candidateSkills;
}
