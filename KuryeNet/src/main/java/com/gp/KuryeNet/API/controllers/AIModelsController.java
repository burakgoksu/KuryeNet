package com.gp.KuryeNet.API.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.core.business.abstracts.AIModelService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.jwt.JwtUtil;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/aimodel")
public class AIModelsController {
	
	private AIModelService aiModelService;
	private JwtUtil jwtUtil;
	
	@Autowired
	public AIModelsController(AIModelService aiModelService, JwtUtil jwtUtil) {
		super();
		this.aiModelService = aiModelService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping("/getPrediction")
	public Mono<ResponseEntity<?>> getPrediction(HttpServletRequest request,@RequestParam int orderId){
		return Mono.fromCallable(()->{
			String token = jwtUtil.extractTokenFromRequest(request);
		    String courierEmail = jwtUtil.extractUsername(token);
			return this.aiModelService.getPrediction(courierEmail,orderId);
		}).map(result -> Utils.getResponseEntity(result));
	}
	
	@GetMapping("/writeData")
	public Mono<ResponseEntity<?>> writeData(HttpServletRequest request,@RequestParam int orderId){
		return Mono.fromCallable(()->{
			String token = jwtUtil.extractTokenFromRequest(request);
		    String courierEmail = jwtUtil.extractUsername(token);
			return this.aiModelService.writeData(courierEmail,orderId);
		}).map(result -> Utils.getResponseEntity(result));
	}
	
	

}
