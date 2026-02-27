package com.gp.KuryeNet.entities.concretes;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.EqualsAndHashCode;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@Table(name="couriers")
@SQLDelete(sql = "UPDATE couriers SET deleted = true, deleted_at = now(), deleted_by = current_user WHERE courier_id = ?")
@SQLRestriction("deleted = false")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","orders"})
public class Courier extends AuditableEntity {
	
	@Id
	@Column(name="courier_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int courierId;
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Istanbul")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@NotNull
	@Column(name="birthday")
	private Date courierBirthday;
	
	//@Column(name="vehicle_id")
	//private int courierVehicleId;
	
    //@Column(name="address_id")
    //private int courierAddressId;
	
	@NotNull
	@NotBlank
	@Column(name="name")
	private String courierName;
	
	@NotNull
	@NotBlank
	@Column(name="surname")
	private String courierSurname;
	
	@NotNull
	@NotBlank
	@Column(name="identity_number",unique = true)
	private String courierIdentityNumber;
	

	@Column(name="latitude")
	private double courierLatitude;
	

	@Column(name="longitude")
	private double courierLongitude;
	
	@NotNull
	@NotBlank
	@Column(name="email",unique = true)
	private String courierEmail;
	
	
	@Column(name="status")
	private int courierStatus;
	
	@Column(name="daily_shipped")
	private int daily_shipped;
	
	@Column(name="total_shipped")
	private int total_shipped;
	
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "Europe/Istanbul")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Column(name="last_daily_update")
	private LocalDate lastDailyUpdate;
	
	@OneToMany(mappedBy = "courier")
	private List<Order> orders;
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="vehicle_id")
	private Vehicle vehicle;
	
	
	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="address_id")
	private Address courierAddress;
}
