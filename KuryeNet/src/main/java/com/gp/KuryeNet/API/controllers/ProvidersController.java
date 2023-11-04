package com.gp.KuryeNet.API.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.business.abstracts.ProviderService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorDataResult;
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
	public ResponseEntity<?> getAll(){
		return Utils.getResponseEntity(this.providerService.getAll());
		
	}
	
	@GetMapping("/getallByPage")
	public ResponseEntity<?> getAll(@RequestParam int pageNo,@RequestParam int pageSize){
		return Utils.getResponseEntity(this.providerService.getAll(pageNo,pageSize));
		
	}
	
	@GetMapping("/getAllSortedByProviderName")
	public ResponseEntity<?> getAllSortedByProviderName(){
		return Utils.getResponseEntity(this.providerService.getAllSortedByProviderName());
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody Provider provider){
		return Utils.getResponseEntity(this.providerService.add(provider));
		
	}
	
	@GetMapping("/getByProviderName")
	public ResponseEntity<?> getByProviderName(@RequestParam String providerName){
		return Utils.getResponseEntity(this.providerService.getByProviderName(providerName));
	}
	
	@GetMapping("/getByProviderType")
	public ResponseEntity<?> getByProviderType(@RequestParam String providerType){
		return Utils.getResponseEntity(this.providerService.getByProviderType(providerType));
	}
	
	@GetMapping("/getByAddressId")
	public ResponseEntity<?> getByAddress_AddressId(@RequestParam int addressId){
		return Utils.getResponseEntity(this.providerService.getByProviderAddress_AddressId(addressId));
	}
	
	@GetMapping("/getByProviderCity")
	public ResponseEntity<?> getByProviderAddress_City(@RequestParam String city){
		return Utils.getResponseEntity(this.providerService.getByProviderAddress_City(city));
	}
	
	@GetMapping("/getByProviderDistrict")
	public ResponseEntity<?> getByProviderAddress_District(@RequestParam String district){
		return Utils.getResponseEntity(this.providerService.getByProviderAddress_District(district));
	}
	
	@GetMapping("/getByProviderCityAndDistrict")
	public ResponseEntity<?> getByProviderAddress_CityAndProviderAddress_District(@RequestParam String city,@RequestParam String district){
		return Utils.getResponseEntity(this.providerService.getByProviderAddress_CityAndProviderAddress_District(city,district));
	}
	
	@GetMapping("/getByProviderId")
	public ResponseEntity<?> getByProviderId(@RequestParam int providerId){
		return Utils.getResponseEntity(this.providerService.getByProviderId(providerId));
	}
	
	@GetMapping("/getByProviderMersisNo")
	public ResponseEntity<?> getByProviderMersisNo(@RequestParam String providerMersisNo){
		return Utils.getResponseEntity(this.providerService.getByProviderMersisNo(providerMersisNo));
	}
	
	
}
