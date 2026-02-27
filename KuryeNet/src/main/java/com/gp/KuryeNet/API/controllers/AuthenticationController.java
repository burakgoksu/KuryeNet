package com.gp.KuryeNet.API.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gp.KuryeNet.core.business.concretes.UserDetailsManager;
import com.gp.KuryeNet.core.entities.AuthenticationRequest;
import com.gp.KuryeNet.core.entities.AuthenticationResponse;
import com.gp.KuryeNet.core.entities.RefreshTokenRequest;
import com.gp.KuryeNet.core.security.LoginAttemptService;
import com.gp.KuryeNet.core.security.TokenBlocklistService;
import com.gp.KuryeNet.core.utulities.jwt.JwtUtil;
import com.gp.KuryeNet.core.utulities.jwt.JwtTokenType;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

	private AuthenticationManager authenticationManager;
	private UserDetailsManager userDetailsManager;
	private JwtUtil jwtUtil;
	private LoginAttemptService loginAttemptService;
	private TokenBlocklistService tokenBlocklistService;
	
	@Autowired
	public AuthenticationController(AuthenticationManager authenticationManager,
			UserDetailsManager userDetailsManager,JwtUtil jwtUtil,
			LoginAttemptService loginAttemptService,
			TokenBlocklistService tokenBlocklistService) {
		super();
		this.authenticationManager = authenticationManager;
		this.userDetailsManager = userDetailsManager;
		this.jwtUtil = jwtUtil;
		this.loginAttemptService = loginAttemptService;
		this.tokenBlocklistService = tokenBlocklistService;
	}
	
	@PostMapping("/login")
	public Mono<DataResult<AuthenticationResponse>> createAuthenticationToken(
			@Valid @RequestBody AuthenticationRequest authenticationRequest,
			HttpServletRequest request,
			HttpServletResponse response) {
	    return Mono.fromCallable(() -> {
	    	String attemptKey = buildAttemptKey(authenticationRequest.getEmail(), request);
	    	if (loginAttemptService.isBlocked(attemptKey)) {
	    		logger.warn("Login blocked due to too many attempts: {}", authenticationRequest.getEmail());
	    		throw new LockedException("Too many failed attempts. Please try later.");
	    	}
	        try {
	            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword()));
	        } catch (BadCredentialsException e) {
	        	loginAttemptService.loginFailed(attemptKey);
	        	logger.warn("Login failed for user: {}", authenticationRequest.getEmail());
	            throw new BadCredentialsException("Incorrect Username or Password");
	        } catch (DisabledException disabledException) {
	            response.sendError(HttpServletResponse.SC_NOT_FOUND, "User is not created. Register User First");
	            return null;
	        }
	        loginAttemptService.loginSucceeded(attemptKey);
	        final UserDetails userDetails = userDetailsManager.loadUserByUsername(authenticationRequest.getEmail());
	        final String accessToken = jwtUtil.generateAccessToken(userDetails);
	        final String refreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
	        AuthenticationResponse authResponse = new AuthenticationResponse(accessToken);
	        authResponse.setAccessToken(accessToken);
	        authResponse.setRefreshToken(refreshToken);
	        authResponse.setTokenType("Bearer");
	        authResponse.setExpiresInSeconds(jwtUtil.getAccessTokenExpirySeconds());
	        logger.info("Login success for user: {}", userDetails.getUsername());
	        return new SuccessDataResult<>(authResponse, "Successfully login and JWT created successfully");
	    });
	}

	@PostMapping("/refresh")
	public Mono<DataResult<AuthenticationResponse>> refreshToken(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		return Mono.fromCallable(() -> {
			String refreshToken = refreshTokenRequest.getRefreshToken();
			String username = jwtUtil.extractUsername(refreshToken);
			UserDetails userDetails = userDetailsManager.loadUserByUsername(username);
			if (!jwtUtil.validateToken(refreshToken, userDetails, JwtTokenType.REFRESH)) {
				throw new BadCredentialsException("Invalid refresh token");
			}
			String tokenId = jwtUtil.extractTokenId(refreshToken);
			if (tokenBlocklistService.isRevoked(tokenId)) {
				throw new BadCredentialsException("Refresh token is revoked");
			}
			long remainingMillis = jwtUtil.getRemainingValidityMillis(refreshToken);
			tokenBlocklistService.revoke(tokenId, java.time.Duration.ofMillis(remainingMillis));
			String accessToken = jwtUtil.generateAccessToken(userDetails);
			String newRefreshToken = jwtUtil.generateRefreshToken(userDetails.getUsername());
			AuthenticationResponse authResponse = new AuthenticationResponse(accessToken);
			authResponse.setAccessToken(accessToken);
			authResponse.setRefreshToken(newRefreshToken);
			authResponse.setTokenType("Bearer");
			authResponse.setExpiresInSeconds(jwtUtil.getAccessTokenExpirySeconds());
			logger.info("Refresh token issued for user: {}", userDetails.getUsername());
			return new SuccessDataResult<>(authResponse, "Access token refreshed successfully");
		});
	}

	private String buildAttemptKey(String email, HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if (ip == null || ip.isBlank()) {
			ip = request.getRemoteAddr();
		} else {
			ip = ip.split(",")[0].trim();
		}
		return email + "|" + ip;
	}
	
	
}
