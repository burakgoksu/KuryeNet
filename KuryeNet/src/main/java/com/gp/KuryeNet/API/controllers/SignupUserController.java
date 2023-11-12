package com.gp.KuryeNet.API.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.gp.KuryeNet.core.business.abstracts.AuthService;
import com.gp.KuryeNet.core.entities.SignupDto;
import com.gp.KuryeNet.core.entities.UserDto;

@RestController
@RequestMapping("/auth")
public class SignupUserController {
	
	private AuthService authService;

	@Autowired
	public SignupUserController(AuthService authService) {
		super();
		this.authService = authService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@RequestBody SignupDto signupDto){
		UserDto createdUser = authService.createUser(signupDto);
		if(createdUser == null)
			return new ResponseEntity<>("User is not created",HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
	}
	

}
