package com.gp.KuryeNet.core.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="users_roles")
public class UserRole {
	
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
