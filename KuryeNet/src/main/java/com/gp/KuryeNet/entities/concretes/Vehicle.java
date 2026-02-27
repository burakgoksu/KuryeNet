package com.gp.KuryeNet.entities.concretes;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

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
@Table(name="vehicles")
@SQLDelete(sql = "UPDATE vehicles SET deleted = true, deleted_at = now(), deleted_by = current_user WHERE vehicle_id = ?")
@SQLRestriction("deleted = false")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","couriers"})
public class Vehicle extends AuditableEntity {
	
	@Id
	@Column(name="vehicle_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int vehicleId;
	
	@NotNull
	@Column(name="vehicle_year")
	private int vehicleYear;
	
	@NotNull
	@Column(name="vehicle_type")
	private String vehicleType;
	
	@NotNull
	@Column(name="vehicle_brand")
	private String vehicleBrand;
	
	@NotNull
	@Column(name="vehicle_model")
	private String vehicleModel;
	
	@NotNull
	@Column(name="vehicle_emission")
	private String vehicleEmission;
	
	@NotNull
	@Column(name="vehicle_plate")
	private String vehiclePlate;
	
	@OneToMany(mappedBy = "vehicle")
	private List<Courier> couriers;
	
}
