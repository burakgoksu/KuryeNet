package com.gp.KuryeNet.API.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.core.business.abstracts.GoogleMapsAPIService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.jwt.JwtUtil;

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
	
	@PostMapping("/getDirection")
	public ResponseEntity<?> getDirection(HttpServletRequest request, @RequestParam int orderId){
		String token = jwtUtil.extractTokenFromRequest(request);
	    String courierEmail = jwtUtil.extractUsername(token);
		return Utils.getResponseEntity(this.googleMapsAPIService.getDirectionsFromGoogleMaps(courierEmail, orderId));
		
	}

}
