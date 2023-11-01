package com.gp.KuryeNet.API.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.business.abstracts.CustomerService;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.entities.concretes.Customer;

@RestController
@RequestMapping("/api/customers")
public class CustomersController {

	private CustomerService customerService;

	public CustomersController(CustomerService customerService) {
		super();
		this.customerService = customerService;
	}
	
	@GetMapping("/getall")
	public DataResult<List<Customer>> getAll(){
		return this.customerService.getAll();
		
	}
	
	@GetMapping("/getallByPage")
	public DataResult<List<Customer>> getAll(int pageNo,int pageSize){
		return this.customerService.getAll(pageNo,pageSize);
		
	}
	
	@GetMapping("/getAllSortedByCustomerName")
	public DataResult<List<Customer>> getAllSortedByCustomerName(){
		return this.customerService.getAllSortedByCustomerName();
		
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody Customer customer){
		return this.customerService.add(customer);
		
	}
	
	@GetMapping("/getByCustomerId")
	public DataResult<Customer> getByCustomerId(int customerId){
		return this.customerService.getByCustomerId(customerId);
		
	}
	
	@GetMapping("/getByCustomerNameAndSurname")
	public DataResult<Customer> getByCustomerNameAndCustomerSurname(String customerName, String customerSurname){
		return this.customerService.getByCustomerNameAndCustomerSurname(customerName,customerSurname);
		
	}
	
	@GetMapping("/getByCustomerEmail")
	public DataResult<Customer> getByCustomerEmail(String customerEmail){
		return this.customerService.getByCustomerEmail(customerEmail);
		
	}
	
	@GetMapping("/getByAddressId")
	public DataResult<Customer> getByCustomerAddress_AddressId(int addressId){
		return this.customerService.getByCustomerAddress_AddressId(addressId);
		
	}
	
	@GetMapping("/getByCity")
	public DataResult<List<Customer>> getByCustomerAddress_City(String city){
		return this.customerService.getByCustomerAddress_City(city);
		
	}
	
}
