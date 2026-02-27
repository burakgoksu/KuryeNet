package com.gp.KuryeNet.API.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;

import com.gp.KuryeNet.business.abstracts.CustomerBasketService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.mapper.ResultMapper;
import com.gp.KuryeNet.core.utulities.jwt.JwtUtil;
import com.gp.KuryeNet.entities.dtos.CustomerBasketDto;


@RestController 
@Validated
@RequestMapping(path = { "${api.base-path:/api}/customersbaskets", "${api.versioned-base-path:/api/v1}/customersbaskets" })
public class CustomerBasketsController {
	
	private CustomerBasketService customerBasketService;
	private JwtUtil jwtUtil;

	@Autowired
	public CustomerBasketsController(CustomerBasketService customerBasketService, JwtUtil jwtUtil) {
		super();
		this.customerBasketService = customerBasketService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping("/getall")
	public ResponseEntity<?> getAll(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.customerBasketService.getAll(), CustomerBasketDto::fromEntity));
		
	}
	
	@GetMapping("/getallByPage")
	public ResponseEntity<?> getAll(@RequestParam @Min(0) int pageNo,@RequestParam @Min(1) @Max(200) int pageSize){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.customerBasketService.getAll(pageNo,pageSize), CustomerBasketDto::fromEntity));
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestParam String orderNumber,HttpServletRequest request){
		String token = jwtUtil.extractTokenFromRequest(request);
	    String customerEmail = jwtUtil.extractUsername(token);
	    
		return Utils.getResponseEntity(this.customerBasketService.add(customerEmail,orderNumber));
		
	}
	
	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam String orderNumber, HttpServletRequest request){
		String token = jwtUtil.extractTokenFromRequest(request);
		String customerEmail = jwtUtil.extractUsername(token);
		
		return Utils.getResponseEntity(this.customerBasketService.delete(orderNumber));
	}
	
	@GetMapping("/getByOrderNumber")
	public ResponseEntity<?> getByOrderNumber(@RequestParam String orderNumber){
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.customerBasketService.getByOrder_OrderNumber(orderNumber), CustomerBasketDto::fromEntity));
	}
	
	@GetMapping("/getByCustomerEmail")
	public ResponseEntity<?> getByCustomerEmail(HttpServletRequest request){
		String token = jwtUtil.extractTokenFromRequest(request);
	    String customerEmail = jwtUtil.extractUsername(token);
		
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.customerBasketService.getByCustomer_CustomerEmail(customerEmail), CustomerBasketDto::fromEntity));
	}

	@GetMapping("/deleted")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getDeleted(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.customerBasketService.getDeleted(), CustomerBasketDto::fromEntity));
	}

	@PostMapping("/restore")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> restore(@RequestParam int customerBasketId){
		return Utils.getResponseEntity(this.customerBasketService.restore(customerBasketId));
	}

}

