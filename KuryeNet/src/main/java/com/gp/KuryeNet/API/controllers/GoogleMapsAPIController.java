package com.gp.KuryeNet.API.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.core.business.abstracts.GoogleMapsAPIService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.jwt.JwtUtil;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/googlemaps")
public class GoogleMapsAPIController {
	
	private GoogleMapsAPIService googleMapsAPIService;
	private JwtUtil jwtUtil;

	@Autowired
	public GoogleMapsAPIController(GoogleMapsAPIService googleMapsAPIService,JwtUtil jwtUtil) {
		super();
		this.googleMapsAPIService = googleMapsAPIService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping("/getRemainingMinutesFromGoogleMaps")
	public Mono<ResponseEntity<?>> getRemainingMinutesFromGoogleMaps(HttpServletRequest request, @RequestParam int orderId){
		return Mono.fromCallable(()->{
			String token = jwtUtil.extractTokenFromRequest(request);
		    String courierEmail = jwtUtil.extractUsername(token);
			return this.googleMapsAPIService.getRemainingMinutesFromGoogleMaps(courierEmail, orderId);
		}).map(result -> Utils.getResponseEntity(result));
	}
	
	@GetMapping("/getDirectionsFromGoogleMaps")
	public Mono<ResponseEntity<?>> getDirectionsFromGoogleMaps(HttpServletRequest request, @RequestParam int orderId){
		return Mono.fromCallable(()->{
			String token = jwtUtil.extractTokenFromRequest(request);
		    String courierEmail = jwtUtil.extractUsername(token);
			return this.googleMapsAPIService.getDirectionsFromGoogleMaps(courierEmail, orderId);
		}).map(result -> Utils.getResponseEntity(result));
	}

}
