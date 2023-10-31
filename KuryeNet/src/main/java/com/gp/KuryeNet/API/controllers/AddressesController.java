package com.gp.KuryeNet.API.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.business.abstracts.AddressService;
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
	public Result add(@RequestBody Address address){
		return this.addressService.add(address);
		
	}

	@GetMapping("/getall")
	public DataResult<List<Address>> getAll(){
		return this.addressService.getAll();
		
	}
	
	@GetMapping("/getallByPage")
	public DataResult<List<Address>> getAll(int pageNo,int pageSize){
		return this.addressService.getAll(pageNo,pageSize);
		
	}
	
	@GetMapping("/getAllSortedByCity")
	public DataResult<List<Address>> getAllSortedByCity(){
		return this.addressService.getAllSortedByCity();
	}
	
	@GetMapping("/getByAddressTitle")
	public DataResult<List<Address>> getByAddressTitle(String addressTitle){
		return this.addressService.getByAddressTitle(addressTitle);
	}
	
	@GetMapping("/getByCity")
	public DataResult<List<Address>> getByCity(String city){
		return this.addressService.getByCity(city);
	}
	
	@GetMapping("/getByPhoneNumber")
	public DataResult<List<Address>> getByPhoneNumber(String phoneNumber){
		return this.addressService.getByPhoneNumber(phoneNumber);
	}
	
}
