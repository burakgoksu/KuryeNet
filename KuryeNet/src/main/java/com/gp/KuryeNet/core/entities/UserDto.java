package com.gp.KuryeNet.core.entities;

import javax.persistence.Entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	private int id;
	
	private String name;
	
	private String surname;
	
//	private String username;
	
	private String email;

//	private String password;
	
//	private String roles;
	
}
