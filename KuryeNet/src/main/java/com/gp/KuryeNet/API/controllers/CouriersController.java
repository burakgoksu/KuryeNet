package com.gp.KuryeNet.API.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.business.abstracts.CourierService;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.entities.concretes.Courier;

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
	public DataResult<List<Courier>> getAll(){
		return this.courierService.getAll();
		
	}
	
	@GetMapping("/getallByPage")
	public DataResult<List<Courier>> getAll(int pageNo,int pageSize){
		return this.courierService.getAll(pageNo,pageSize);
		
	}
	
	@GetMapping("/getAllSortedByCourierName")
	public DataResult<List<Courier>> getAllSortedByCourierName(){
		return this.courierService.getAllSortedByCourierName();
		
	}
	
	
	@PostMapping("/add")
	public Result add(@RequestBody Courier courier){
		return this.courierService.add(courier);
		
	}
	
	@GetMapping("/getByCourierNameAndSurname")
	public DataResult<Courier> getByCourierNameAndCourierSurname(@RequestParam("name") String name, @RequestParam("surname") String surname){
		return this.courierService.getByCourierNameAndCourierSurname(name, surname);
	}
	
	@GetMapping("/getByCourierIdentityNumber")
	public DataResult<Courier> getByCourierIdentityNumber(@RequestParam String courierIdentityNumber){
		return this.courierService.getByCourierIdentityNumber(courierIdentityNumber);
	}
	
	@GetMapping("/getByCourierEmail")
	public DataResult<Courier> getByCourierEmail(@RequestParam String courierEmail){
		return this.courierService.getByCourierEmail(courierEmail);
	}
}
