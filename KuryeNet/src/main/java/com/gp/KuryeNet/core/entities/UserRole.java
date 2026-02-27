package com.gp.KuryeNet.core.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.gp.KuryeNet.core.entities.AuditableEntity;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users_roles")
@SQLDelete(sql = "UPDATE users_roles SET deleted = true, deleted_at = now(), deleted_by = current_user WHERE user_role_id = ?")
@SQLRestriction("deleted = false")
public class UserRole extends AuditableEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="user_role_id")
	private int userRoleId;
	
	/*
	@Column(name="role_id")
	@NotBlank
	@NotNull
	private int roleId;
	
	@Column(name="user_id")
	@NotBlank
	@NotNull
	private int userId;
	*/
	
	@ManyToOne
	@JoinColumn(name="role_id",referencedColumnName="role_id")
	private Role role;
	
	@ManyToOne
	@JoinColumn(name="user_id",referencedColumnName="id")
	private User user;
}
