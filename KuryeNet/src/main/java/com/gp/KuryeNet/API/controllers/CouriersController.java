package com.gp.KuryeNet.API.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;


import com.gp.KuryeNet.business.abstracts.CourierService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.jwt.JwtUtil;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.entities.concretes.Courier;
import com.gp.KuryeNet.entities.dtos.CourierWithVehicleDto;

@RestController
@RequestMapping("/api/couriers")
public class CouriersController {

	private CourierService courierService;
	private JwtUtil jwtUtil;

	@Autowired
	public CouriersController(CourierService courierService, JwtUtil jwtUtil) {
		super();
		this.courierService = courierService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAll(){
		return Utils.getResponseEntity(this.courierService.getAll()); 
		
	}
	
	@GetMapping("/getallByPage")
	public ResponseEntity<?> getAll(int pageNo,int pageSize){
		return Utils.getResponseEntity(this.courierService.getAll(pageNo,pageSize));
		
	}
	
	@GetMapping("/getAllSortedByCourierName")
	public ResponseEntity<?> getAllSortedByCourierName(){
		return Utils.getResponseEntity(this.courierService.getAllSortedByCourierName());
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody Courier courier){
		return Utils.getResponseEntity(this.courierService.add(courier)) ;
		
	}
	
	@GetMapping("/getByCourierNameAndSurname")
	public ResponseEntity<?> getByCourierNameAndCourierSurname(@RequestParam("name") String name, @RequestParam("surname") String surname){
		return Utils.getResponseEntity(this.courierService.getByCourierNameAndCourierSurname(name, surname));
	}
	
	@GetMapping("/getByCourierIdentityNumber")
	public ResponseEntity<?> getByCourierIdentityNumber(@RequestParam String courierIdentityNumber){
		return Utils.getResponseEntity(this.courierService.getByCourierIdentityNumber(courierIdentityNumber));
	}
	
	@GetMapping("/getByCourierEmail")
	public ResponseEntity<?> getByCourierEmail(@RequestParam String courierEmail){
		return Utils.getResponseEntity(this.courierService.getByCourierEmail(courierEmail));
	}
	
	@GetMapping("/getByCourierCity")
	public ResponseEntity<?> getByCourierAddress_City(@RequestParam String city){
		return Utils.getResponseEntity(this.courierService.getByCourierAddress_City(city));
	}
	
	@GetMapping("/getCourierWithVehicleDetails")
	public ResponseEntity<?> getCourierWithVehicleDetails(){
		return Utils.getResponseEntity(this.courierService.getCourierWithVehicleDetails());
	}
	
	@GetMapping("/getById")
	public ResponseEntity<?> getByCourierId(@Valid @RequestParam int addressId){
		return Utils.getResponseEntity(this.courierService.getByCourierId(addressId)); 
	}
	
	@GetMapping("/getByCourierIdWithOrderId")
	public ResponseEntity<?> getByCourierIdWithOrderId(@Valid @RequestParam int orderId){
		return Utils.getResponseEntity(this.courierService.getByCourierIdWithOrderId(orderId)); 
	}
	
	@PostMapping("/startOrder")
	public ResponseEntity<?> startOrder(@Valid @RequestParam int orderId, HttpServletRequest request){
		String token = jwtUtil.extractTokenFromRequest(request);
	    String courierEmail = jwtUtil.extractUsername(token);
	    
		return Utils.getResponseEntity(this.courierService.startOrder(orderId,courierEmail));
		
	}
	
	@PostMapping("/endOrder")
	public ResponseEntity<?> endOrder(@Valid @RequestParam int orderId, HttpServletRequest request){
		String token = jwtUtil.extractTokenFromRequest(request);
	    String courierEmail = jwtUtil.extractUsername(token);
	    
		return Utils.getResponseEntity(this.courierService.endOrder(orderId,courierEmail));
		
	}
	
	@PutMapping("/updateCourierCoordinates")
	public ResponseEntity<?> updateCourierCoordinates(HttpServletRequest request, @RequestParam double latitude, @RequestParam double longitude){
		
	    String token = jwtUtil.extractTokenFromRequest(request);
	    String courierEmail = jwtUtil.extractUsername(token);
		
		return Utils.getResponseEntity(this.courierService.updateCourierCoordinates(courierEmail,latitude,longitude));
		
	}
	
	
}
