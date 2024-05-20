package com.gp.KuryeNet.API.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.business.abstracts.CustomerBasketService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.jwt.JwtUtil;


@RestController 
@RequestMapping("/api/customersbaskets")
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
		return Utils.getResponseEntity(this.customerBasketService.getAll());
		
	}
	
	@GetMapping("/getallByPage")
	public ResponseEntity<?> getAll(@RequestParam int pageNo,@RequestParam int pageSize){
		return Utils.getResponseEntity(this.customerBasketService.getAll(pageNo,pageSize));
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestParam String orderNumber,HttpServletRequest request){
		String token = jwtUtil.extractTokenFromRequest(request);
	    String customerEmail = jwtUtil.extractUsername(token);
	    
		return Utils.getResponseEntity(this.customerBasketService.add(customerEmail,orderNumber));
		
	}
	
	@GetMapping("/getByOrderNumber")
	public ResponseEntity<?> getByOrderNumber(@RequestParam String orderNumber){
		return Utils.getResponseEntity(this.customerBasketService.getByOrder_OrderNumber(orderNumber));
	}
	
	@GetMapping("/getByCustomerEmail")
	public ResponseEntity<?> getByCustomerEmail(HttpServletRequest request){
		String token = jwtUtil.extractTokenFromRequest(request);
	    String customerEmail = jwtUtil.extractUsername(token);
		
		return Utils.getResponseEntity(this.customerBasketService.getByCustomer_CustomerEmail(customerEmail));
	}

}
