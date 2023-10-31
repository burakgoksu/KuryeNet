package com.gp.KuryeNet.API.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.business.abstracts.ProviderService;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.entities.concretes.Courier;
import com.gp.KuryeNet.entities.concretes.Provider;

@RestController
@RequestMapping("/api/providers")
public class ProvidersController {
	
	private ProviderService providerService;

	@Autowired
	public ProvidersController(ProviderService providerService) {
		super();
		this.providerService = providerService;
	}
	
	@GetMapping("/getall")
	public DataResult<List<Provider>> getAll(){
		return this.providerService.getAll();
		
	}
	
	@GetMapping("/getallByPage")
	public DataResult<List<Provider>> getAll(int pageNo,int pageSize){
		return this.providerService.getAll(pageNo,pageSize);
		
	}
	
	@GetMapping("/getAllSortedByProviderName")
	public DataResult<List<Provider>> getAllSortedByProviderName(){
		return this.providerService.getAllSortedByProviderName();
		
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody Provider provider){
		return this.providerService.add(provider);
		
	}
	
	@GetMapping("/getByProviderName")
	public DataResult<Provider> getByProviderName(String providerName){
		return this.providerService.getByProviderName(providerName);
	}
	
	@GetMapping("/getByProviderType")
	public DataResult<List<Provider>> getByProviderType(String providerType){
		return this.providerService.getByProviderType(providerType);
	}
	
	@GetMapping("/getByAddressId")
	public DataResult<Provider> getByAddress_AddressId(int addressId){
		return this.providerService.getByProviderAddress_AddressId(addressId);
	}
	
	@GetMapping("/getByProviderCity")
	public DataResult<List<Provider>> getByProviderAddress_City(String city){
		return this.providerService.getByProviderAddress_City(city);
	}
	
	@GetMapping("/getByProviderDistrict")
	public DataResult<List<Provider>> getByProviderAddress_District(String district){
		return this.providerService.getByProviderAddress_District(district);
	}
	
	@GetMapping("/getByProviderCityAndDistrict")
	public DataResult<List<Provider>> getByProviderAddress_CityAndProviderAddress_District(String city,String district){
		return this.providerService.getByProviderAddress_CityAndProviderAddress_District(city,district);
	}
	
	

	
}
