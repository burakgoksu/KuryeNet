package com.gp.KuryeNet.business.abstracts;

import java.util.Date;
import java.util.List;

import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.entities.concretes.Customer;

public interface CustomerService {

	DataResult<List<Customer>> getAll();
	
	DataResult<List<Customer>> getAll(int pageNo, int pageSize);
	
	DataResult<List<Customer>> getAllSortedByCustomerName();

	Result add(Customer customer);
	
	DataResult<Customer> getByCustomerId(int customerId);
	
	DataResult<Customer> getByCustomerNameAndCustomerSurname(String customerName,String customerSurname);
	
	DataResult<Customer> getByCustomerEmail(String customerEmail);
	
	DataResult<Customer> getByCustomerAddress_AddressId(int addressId);
	
	DataResult<List<Customer>> getByCustomerAddress_City(String city);
	
}
