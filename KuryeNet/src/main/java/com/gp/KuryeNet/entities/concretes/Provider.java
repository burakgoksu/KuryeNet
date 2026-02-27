package com.gp.KuryeNet.entities.concretes;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.gp.KuryeNet.core.entities.AuditableEntity;
import lombok.EqualsAndHashCode;
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
@Table(name="providers")
@SQLDelete(sql = "UPDATE providers SET deleted = true, deleted_at = now(), deleted_by = current_user WHERE provider_id = ?")
@SQLRestriction("deleted = false")
@JsonIgnoreProperties({"hibernateLazyInitializer","handler","orders"})
public class Provider extends AuditableEntity {
	
	@Id
	@Column(name="provider_id")
	private int providerId;
	
//	@Column(name="address_id")
//	private int addressId;
	
	@NotNull
    @Pattern(regexp = "^(restoran|market|kafe/bistro)$")
	@Column(name="provider_type")
	private String providerType;
	
	@NotNull
	@Column(name="provider_name")
	private String providerName;
	
	@Column(name="latitude")
	private double providerLatitude;
	
	@Column(name="longitude")
	private double providerLongitude;
	
	@Column(name="mersis_no")
	private String providerMersisNo;
	
	@ManyToOne
	@JoinColumn(name="address_id")
	private Address providerAddress;
	
	@OneToMany(mappedBy="provider")
	private List<Order> orders;

}
