package com.gp.KuryeNet.API.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;

import com.gp.KuryeNet.business.abstracts.CustomerService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.mapper.ResultMapper;
import com.gp.KuryeNet.core.utulities.jwt.JwtUtil;
import com.gp.KuryeNet.entities.dtos.CustomerDto;
import com.gp.KuryeNet.entities.dtos.requests.CustomerCreateRequest;
import com.gp.KuryeNet.entities.dtos.requests.CustomerUpdateRequest;

@RestController 
@Validated
@RequestMapping(path = { "${api.base-path:/api}/customers", "${api.versioned-base-path:/api/v1}/customers" })
public class CustomersController {

	private CustomerService customerService;
	private JwtUtil jwtUtil;

	public CustomersController(CustomerService customerService, JwtUtil jwtUtil) {
		super();
		this.customerService = customerService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAll(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.customerService.getAll(), CustomerDto::fromEntity));
		
	}
	
	@GetMapping("/getallByPage")
	public ResponseEntity<?> getAll(@RequestParam @Min(0) int pageNo,@RequestParam @Min(1) @Max(200) int pageSize){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.customerService.getAll(pageNo,pageSize), CustomerDto::fromEntity));
		
	}
	
	@GetMapping("/getAllSortedByCustomerName")
	public ResponseEntity<?> getAllSortedByCustomerName(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.customerService.getAllSortedByCustomerName(), CustomerDto::fromEntity));
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody CustomerCreateRequest request){
		return Utils.getResponseEntity(this.customerService.add(request.toEntity()));
		
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> update(HttpServletRequest request,@Valid @RequestBody CustomerUpdateRequest customerRequest){
		String token = jwtUtil.extractTokenFromRequest(request);
	    String customerEmail = jwtUtil.extractUsername(token);
	    
		return Utils.getResponseEntity(this.customerService.update(customerEmail, customerRequest.toEntity()));
		
	}
	
	@GetMapping("/getByCustomerId")
	public ResponseEntity<?> getByCustomerId(int customerId){
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.customerService.getByCustomerId(customerId), CustomerDto::fromEntity));
		
	}
	
	@GetMapping("/getByCustomerNameAndSurname")
	public ResponseEntity<?> getByCustomerNameAndCustomerSurname(String customerName, String customerSurname){
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.customerService.getByCustomerNameAndCustomerSurname(customerName,customerSurname), CustomerDto::fromEntity));
		
	}
	
	@GetMapping("/getByCustomerEmail")
	public ResponseEntity<?> getByCustomerEmail(HttpServletRequest request){
		String token = jwtUtil.extractTokenFromRequest(request);
	    String customerEmail = jwtUtil.extractUsername(token);
		
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.customerService.getByCustomerEmail(customerEmail), CustomerDto::fromEntity));
		
	}
	
	@GetMapping("/getByAddressId")
	public ResponseEntity<?> getByCustomerAddress_AddressId(int addressId){
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.customerService.getByCustomerAddress_AddressId(addressId), CustomerDto::fromEntity));
		
	}
	
	@GetMapping("/getByCity")
	public ResponseEntity<?> getByCustomerAddress_City(String city){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.customerService.getByCustomerAddress_City(city), CustomerDto::fromEntity));
		
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> delete(@RequestParam int customerId){
		return Utils.getResponseEntity(this.customerService.delete(customerId));
	}

	@PostMapping("/restore")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> restore(@RequestParam int customerId){
		return Utils.getResponseEntity(this.customerService.restore(customerId));
	}

	@GetMapping("/deleted")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getDeleted(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.customerService.getDeleted(), CustomerDto::fromEntity));
	}
	
}

