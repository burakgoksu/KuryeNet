package com.gp.KuryeNet.API.controllers;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.business.abstracts.CustomerService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.jwt.JwtUtil;
import com.gp.KuryeNet.entities.concretes.Customer;

@RestController 
@RequestMapping("/api/customers")
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
		return Utils.getResponseEntity(this.customerService.getAll());
		
	}
	
	@GetMapping("/getallByPage")
	public ResponseEntity<?> getAll(int pageNo,int pageSize){
		return Utils.getResponseEntity(this.customerService.getAll(pageNo,pageSize));
		
	}
	
	@GetMapping("/getAllSortedByCustomerName")
	public ResponseEntity<?> getAllSortedByCustomerName(){
		return Utils.getResponseEntity(this.customerService.getAllSortedByCustomerName());
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@RequestBody Customer customer){
		return Utils.getResponseEntity(this.customerService.add(customer));
		
	}
	
	@PostMapping("/update")
	public ResponseEntity<?> update(HttpServletRequest request,@RequestBody Customer customer){
		String token = jwtUtil.extractTokenFromRequest(request);
	    String customerEmail = jwtUtil.extractUsername(token);
	    
		return Utils.getResponseEntity(this.customerService.update(customerEmail,customer));
		
	}
	
	@GetMapping("/getByCustomerId")
	public ResponseEntity<?> getByCustomerId(int customerId){
		return Utils.getResponseEntity(this.customerService.getByCustomerId(customerId));
		
	}
	
	@GetMapping("/getByCustomerNameAndSurname")
	public ResponseEntity<?> getByCustomerNameAndCustomerSurname(String customerName, String customerSurname){
		return Utils.getResponseEntity(this.customerService.getByCustomerNameAndCustomerSurname(customerName,customerSurname));
		
	}
	
	@GetMapping("/getByCustomerEmail")
	public ResponseEntity<?> getByCustomerEmail(HttpServletRequest request){
		String token = jwtUtil.extractTokenFromRequest(request);
	    String customerEmail = jwtUtil.extractUsername(token);
		
		return Utils.getResponseEntity(this.customerService.getByCustomerEmail(customerEmail));
		
	}
	
	@GetMapping("/getByAddressId")
	public ResponseEntity<?> getByCustomerAddress_AddressId(int addressId){
		return Utils.getResponseEntity(this.customerService.getByCustomerAddress_AddressId(addressId));
		
	}
	
	@GetMapping("/getByCity")
	public ResponseEntity<?> getByCustomerAddress_City(String city){
		return Utils.getResponseEntity(this.customerService.getByCustomerAddress_City(city));
		
	}
	
}
