package com.gp.KuryeNet.API.controllers;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;

import com.gp.KuryeNet.business.abstracts.ProviderService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.mapper.ResultMapper;
import com.gp.KuryeNet.entities.dtos.ProviderDto;
import com.gp.KuryeNet.entities.dtos.requests.ProviderCreateRequest;

@RestController
@Validated
@RequestMapping(path = { "${api.base-path:/api}/providers", "${api.versioned-base-path:/api/v1}/providers" })
public class ProvidersController {
	
	private ProviderService providerService;

	@Autowired
	public ProvidersController(ProviderService providerService) {
		super();
		this.providerService = providerService;
	}
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAll(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.providerService.getAll(), ProviderDto::fromEntity));
		
	}
	
	@GetMapping("/getallByPage")
	public ResponseEntity<?> getAll(@RequestParam @Min(0) int pageNo,@RequestParam @Min(1) @Max(200) int pageSize){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.providerService.getAll(pageNo,pageSize), ProviderDto::fromEntity));
		
	}
	
	@GetMapping("/getAllSortedByProviderName")
	public ResponseEntity<?> getAllSortedByProviderName(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.providerService.getAllSortedByProviderName(), ProviderDto::fromEntity));
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody ProviderCreateRequest providerRequest){
		return Utils.getResponseEntity(this.providerService.add(providerRequest.toEntity()));
		
	}
	
	@GetMapping("/getByProviderName")
	public ResponseEntity<?> getByProviderName(@RequestParam String providerName){
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.providerService.getByProviderName(providerName), ProviderDto::fromEntity));
	}
	
	@GetMapping("/getByProviderType")
	public ResponseEntity<?> getByProviderType(@RequestParam String providerType){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.providerService.getByProviderType(providerType), ProviderDto::fromEntity));
	}
	
	@GetMapping("/getByAddressId")
	public ResponseEntity<?> getByAddress_AddressId(@RequestParam int addressId){
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.providerService.getByProviderAddress_AddressId(addressId), ProviderDto::fromEntity));
	}
	
	@GetMapping("/getByProviderCity")
	public ResponseEntity<?> getByProviderAddress_City(@RequestParam String city){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.providerService.getByProviderAddress_City(city), ProviderDto::fromEntity));
	}
	
	@GetMapping("/getByProviderDistrict")
	public ResponseEntity<?> getByProviderAddress_District(@RequestParam String district){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.providerService.getByProviderAddress_District(district), ProviderDto::fromEntity));
	}
	
	@GetMapping("/getByProviderCityAndDistrict")
	public ResponseEntity<?> getByProviderAddress_CityAndProviderAddress_District(@RequestParam String city,@RequestParam String district){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.providerService.getByProviderAddress_CityAndProviderAddress_District(city,district), ProviderDto::fromEntity));
	}
	
	@GetMapping("/getByProviderId")
	public ResponseEntity<?> getByProviderId(@RequestParam int providerId){
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.providerService.getByProviderId(providerId), ProviderDto::fromEntity));
	}
	
	@GetMapping("/getByProviderMersisNo")
	public ResponseEntity<?> getByProviderMersisNo(@RequestParam String providerMersisNo){
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.providerService.getByProviderMersisNo(providerMersisNo), ProviderDto::fromEntity));
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> delete(@RequestParam int providerId){
		return Utils.getResponseEntity(this.providerService.delete(providerId));
	}

	@PostMapping("/restore")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> restore(@RequestParam int providerId){
		return Utils.getResponseEntity(this.providerService.restore(providerId));
	}

	@GetMapping("/deleted")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getDeleted(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.providerService.getDeleted(), ProviderDto::fromEntity));
	}
	
	
}

