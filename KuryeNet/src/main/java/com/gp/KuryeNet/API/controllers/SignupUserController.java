package com.gp.KuryeNet.API.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


import com.gp.KuryeNet.core.business.abstracts.AuthService;
import com.gp.KuryeNet.core.entities.SignupDto;
import com.gp.KuryeNet.core.entities.UserDto;
import com.gp.KuryeNet.core.utulities.Util.Utils;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:8081")
public class SignupUserController {
	
	private AuthService authService;

	@Autowired
	public SignupUserController(AuthService authService) {
		super();
		this.authService = authService;
	}
	
	@PostMapping("/register")
	public Mono<ResponseEntity<?>> createUser(@RequestBody SignupDto signupDto){
		return Mono.fromCallable(()->{
	//		UserDto createdUser = authService.createUser(signupDto);
	//		if(createdUser == null)
	//			return new ResponseEntity<>("User is not created",HttpStatus.BAD_REQUEST);
	//		return new ResponseEntity<>(createdUser,HttpStatus.CREATED);
			
			return this.authService.createUser(signupDto);
		}).map(result -> Utils.getResponseEntity(result));
	}
	

}
