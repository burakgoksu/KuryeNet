package com.gp.KuryeNet.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.gp.KuryeNet.entities.concretes.Customer;


@Repository
@Component
public interface CustomerDao extends JpaRepository<Customer,Integer>{

	Customer getByCustomerId(int customerId);
	
	Customer getByCustomerNameAndCustomerSurname(String customerName,String customerSurname);
	
	Customer getByCustomerEmail(String customerEmail);
	
	Customer getByCustomerAddress_AddressId(int addressId);
	
	List<Customer> getByCustomerAddress_City(String city);
}
