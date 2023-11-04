package com.gp.KuryeNet.dataAccess.abstracts;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import com.gp.KuryeNet.entities.concretes.Provider;

@Repository
@Component
@Transactional
public interface ProviderDao extends JpaRepository<Provider,Integer>{
	
	Provider getByProviderName(String providerName);
	
	List<Provider> getByProviderType(String providerType);
	
	Provider getByProviderAddress_AddressId(int addressId);
	
	List<Provider> getByProviderAddress_City(String addressCity);
	
	List<Provider> getByProviderAddress_District(String addressDistrict);
	
	List<Provider> getByProviderAddress_CityAndProviderAddress_District(String addressCity, String addressDistrict);
	
}
