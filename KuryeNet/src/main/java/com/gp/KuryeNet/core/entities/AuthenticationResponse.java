package com.gp.KuryeNet.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthenticationResponse {

	private String jwt;
	private String accessToken;
	private String refreshToken;
	private String tokenType;
	private long expiresInSeconds;

	public AuthenticationResponse(String jwt) {
		this.jwt = jwt;
		this.accessToken = jwt;
	}
}
