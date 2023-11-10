package com.gp.KuryeNet.API.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.core.business.abstracts.GoogleMapsAPIService;
import com.gp.KuryeNet.core.utulities.Util.Utils;

@RestController
@RequestMapping("/api/googlemaps")
public class GoogleMapsAPIController {
	
	private GoogleMapsAPIService googleMapsAPIService;

	
	public GoogleMapsAPIController(GoogleMapsAPIService googleMapsAPIService) {
		super();
		this.googleMapsAPIService = googleMapsAPIService;
	}
	
	@PostMapping("/getDirection")
	public ResponseEntity<?> getDirection(@RequestParam int courierId, @RequestParam int customerId){
		return Utils.getResponseEntity(this.googleMapsAPIService.getDirectionsFromGoogleMaps(courierId, customerId));
		
	}

}
