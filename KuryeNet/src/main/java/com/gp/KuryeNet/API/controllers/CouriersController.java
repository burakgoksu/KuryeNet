package com.gp.KuryeNet.API.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.business.abstracts.CourierService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.entities.concretes.Courier;
import com.gp.KuryeNet.entities.dtos.CourierWithVehicleDto;

@RestController
@RequestMapping("/api/couriers")
public class CouriersController {

	private CourierService courierService;

	@Autowired
	public CouriersController(CourierService courierService) {
		super();
		this.courierService = courierService;
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
}
