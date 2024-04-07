package com.gp.KuryeNet.API.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.core.business.abstracts.GoogleMapsAPIService;
import com.gp.KuryeNet.core.business.abstracts.OpenWeatherMapService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.jwt.JwtUtil;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/openweathermap")
public class OpenWeatherMapController {
	
	private OpenWeatherMapService openWeatherMapService;
	private JwtUtil jwtUtil;
	
	@Autowired
	public OpenWeatherMapController(OpenWeatherMapService openWeatherMapService,JwtUtil jwtUtil) {
		super();
		this.openWeatherMapService = openWeatherMapService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping("/getWeather")
	public Mono<ResponseEntity<?>> getRemainingDataFromGoogleMaps(HttpServletRequest request){
		return Mono.fromCallable(()->{
			String token = jwtUtil.extractTokenFromRequest(request);
		    String courierEmail = jwtUtil.extractUsername(token);
			return this.openWeatherMapService.getWeather(courierEmail);
		}).map(result -> Utils.getResponseEntity(result));
	}
	

}
