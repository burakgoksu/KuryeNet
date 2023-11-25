package com.gp.KuryeNet.core.entities;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupDto {

	private String name;
	
	private String surname;
	
	private String email;
	
	private String password;
}
