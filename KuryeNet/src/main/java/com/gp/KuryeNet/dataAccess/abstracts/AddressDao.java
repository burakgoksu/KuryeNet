package com.gp.KuryeNet.dataAccess.abstracts;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.gp.KuryeNet.entities.concretes.Address;

@Repository
@Component
@Transactional
public interface AddressDao extends JpaRepository<Address,Integer>{
	
	List<Address> getByAddressTitle(String addressTitle);
	
	List<Address> getByPhoneNumber(String phoneNumber);
	
	List<Address> getByCity(String city);
	
	Address getByAddressId(int id);
	
	boolean existsByAddressId(String nationalityId);
	
	boolean existsByPhoneNumber(String nationalityId);
	
}
