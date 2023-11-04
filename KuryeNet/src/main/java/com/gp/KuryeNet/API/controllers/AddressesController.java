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
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.business.abstracts.AddressService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.entities.concretes.Address;

@RestController
@RequestMapping("/api/addresses")
public class AddressesController {
	
	private AddressService addressService;

	@Autowired
	public AddressesController(AddressService addressService) {
		super();
		this.addressService = addressService;
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody Address address){
		return Utils.getResponseEntity(this.addressService.add(address));
		
	}

	@GetMapping("/getall")
	public ResponseEntity<?> getAll(){
		return Utils.getResponseEntity(this.addressService.getAll());
		
	}
	
	@GetMapping("/getallByPage")
	public ResponseEntity<?> getAll(@RequestParam int pageNo, @RequestParam int pageSize){
		return Utils.getResponseEntity(this.addressService.getAll(pageNo,pageSize));
		
	}
	
	@GetMapping("/getAllSortedByCity")
	public ResponseEntity<?> getAllSortedByCity(){
		return Utils.getResponseEntity(this.addressService.getAllSortedByCity());
	}
	
	@GetMapping("/getByAddressTitle")
	public ResponseEntity<?> getByAddressTitle(@Valid @RequestParam String addressTitle){
		return Utils.getResponseEntity(this.addressService.getByAddressTitle(addressTitle));
	}
	
	@GetMapping("/getByCity")
	public ResponseEntity<?> getByCity(@Valid @RequestParam String city){
		return Utils.getResponseEntity(this.addressService.getByCity(city));
	}
	
	@GetMapping("/getByPhoneNumber")
	public ResponseEntity<?> getByPhoneNumber(@Valid @RequestParam String phoneNumber){
		return Utils.getResponseEntity(this.addressService.getByPhoneNumber(phoneNumber));
	}
	
	@GetMapping("/getById")
	public ResponseEntity<?> getByAddressId(@Valid @RequestParam int id){
		return Utils.getResponseEntity(this.addressService.getByAddressId(id)); 
	}
	
}
