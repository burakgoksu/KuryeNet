package com.gp.KuryeNet.dataAccess.abstracts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.gp.KuryeNet.entities.concretes.Address;

@Repository
@Component
public interface AddressDao extends JpaRepository<Address,Integer>{
	
	List<Address> getByAddressTitle(String addressTitle);
	
	List<Address> getByPhoneNumber(String phoneNumber);
	
	List<Address> getByCity(String city);
	
}
