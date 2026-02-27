package com.gp.KuryeNet.API.controllers;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.business.abstracts.AddressService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.mapper.ResultMapper;
import com.gp.KuryeNet.entities.concretes.Address;
import com.gp.KuryeNet.entities.dtos.AddressDto;
import com.gp.KuryeNet.entities.dtos.requests.AddressRequest;

import reactor.core.publisher.Mono;

@RestController
@Validated
@RequestMapping(path = { "${api.base-path:/api}/addresses", "${api.versioned-base-path:/api/v1}/addresses" })
public class AddressesController {
	
	private AddressService addressService;

	@Autowired
	public AddressesController(AddressService addressService) {
		super();
		this.addressService = addressService;
	}
	
	@PostMapping("/add")
	public Mono<ResponseEntity<?>> add(@Valid @RequestBody AddressRequest addressRequest){
		return Mono.fromCallable(()->{
			Address address = addressRequest.toEntity();
			return this.addressService.add(address);
		}).map(result -> Utils.getResponseEntity(result));
		
	}

	@GetMapping("/getall")
	public Mono<ResponseEntity<?>> getAll(){
		return Mono.fromCallable(()->{
			return this.addressService.getAll();
		}).map(result -> Utils.getResponseEntity(ResultMapper.mapListIfSuccess(result, AddressDto::fromEntity)));
	}
	
	@GetMapping("/getallByPage")
	public ResponseEntity<?> getAll(@RequestParam @Min(0) int pageNo, @RequestParam @Min(1) @Max(200) int pageSize){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.addressService.getAll(pageNo,pageSize), AddressDto::fromEntity));
		
	}
	
	@GetMapping("/getAllSortedByCity")
	public ResponseEntity<?> getAllSortedByCity(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.addressService.getAllSortedByCity(), AddressDto::fromEntity));
	}
	
	@GetMapping("/getByAddressTitle")
	public ResponseEntity<?> getByAddressTitle(@Valid @RequestParam String addressTitle){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.addressService.getByAddressTitle(addressTitle), AddressDto::fromEntity));
	}
	
	@GetMapping("/getByCity")
	public ResponseEntity<?> getByCity(@Valid @RequestParam String city){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.addressService.getByCity(city), AddressDto::fromEntity));
	}
	
	@GetMapping("/getByPhoneNumber")
	public ResponseEntity<?> getByPhoneNumber(@Valid @RequestParam String phoneNumber){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.addressService.getByPhoneNumber(phoneNumber), AddressDto::fromEntity));
	}
	
	@GetMapping("/getById")
	public ResponseEntity<?> getByAddressId(@Valid @RequestParam int id){
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.addressService.getByAddressId(id), AddressDto::fromEntity)); 
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> delete(@RequestParam int addressId){
		return Utils.getResponseEntity(this.addressService.delete(addressId));
	}

	@PostMapping("/restore")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> restore(@RequestParam int addressId){
		return Utils.getResponseEntity(this.addressService.restore(addressId));
	}

	@GetMapping("/deleted")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getDeleted(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.addressService.getDeleted(), AddressDto::fromEntity));
	}
	
}

