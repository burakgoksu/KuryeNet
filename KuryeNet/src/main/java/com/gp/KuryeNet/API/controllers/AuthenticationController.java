package com.gp.KuryeNet.API.controllers;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.core.business.concretes.UserDetailsManager;
import com.gp.KuryeNet.core.entities.AuthenticationRequest;
import com.gp.KuryeNet.core.entities.AuthenticationResponse;
import com.gp.KuryeNet.core.utulities.jwt.JwtUtil;

import io.jsonwebtoken.io.IOException;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	private AuthenticationManager authenticationManager;
	private UserDetailsManager userDetailsManager;
	private JwtUtil jwtUtil;
	
	@Autowired
	public AuthenticationController(AuthenticationManager authenticationManager,
			UserDetailsManager userDetailsManager,JwtUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.userDetailsManager = userDetailsManager;
		this.jwtUtil = jwtUtil;
	}
	
	@PostMapping("/login")
	public AuthenticationResponse createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest,HttpServletResponse response) throws BadCredentialsException, DisabledException, UsernameNotFoundException,IOException, java.io.IOException{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
		}
		catch(BadCredentialsException e){
			throw new BadCredentialsException("Incorrect Username or Password");
		}
		
		catch(DisabledException disabledException){
			response.sendError(HttpServletResponse.SC_NOT_FOUND,"User is not created. Register User First");
			return null;
		}
		final UserDetails userDetails = userDetailsManager.loadUserByUsername(authenticationRequest.getEmail());
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());
		return new AuthenticationResponse(jwt);
	}
	
	
}
