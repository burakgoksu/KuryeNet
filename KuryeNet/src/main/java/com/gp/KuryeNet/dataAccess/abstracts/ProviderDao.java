package com.gp.KuryeNet.dataAccess.abstracts;

import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.time.Instant;
import com.gp.KuryeNet.entities.concretes.Provider;

@Repository
@Component
@Transactional
public interface ProviderDao extends JpaRepository<Provider,Integer>{
	
	Provider getByProviderName(String providerName);
	
	List<Provider> getByProviderType(String providerType);
	
	Provider getByProviderAddress_AddressId(int addressId);
	
	Provider getByProviderId(int providerId);
	
	Provider getByProviderMersisNo(String providerMersisNo);
	
	boolean existsByProviderMersisNo(String providerMersisNo);
	
	List<Provider> getByProviderAddress_City(String addressCity);
	
	List<Provider> getByProviderAddress_District(String addressDistrict);
	
	List<Provider> getByProviderAddress_CityAndProviderAddress_District(String addressCity, String addressDistrict);

	@Query(value = "SELECT * FROM providers WHERE deleted = true", nativeQuery = true)
	List<Provider> getDeleted();

	@Modifying
	@Query("UPDATE Provider p SET p.deleted = false, p.deletedAt = null, p.deletedBy = null WHERE p.providerId = :id")
	int restoreById(@Param("id") int id);

	@Modifying
	@Query("UPDATE Provider p SET p.deleted = true, p.deletedAt = :deletedAt, p.deletedBy = :deletedBy WHERE p.providerId = :id")
	int softDeleteById(@Param("id") int id, @Param("deletedAt") Instant deletedAt, @Param("deletedBy") String deletedBy);
	
}
