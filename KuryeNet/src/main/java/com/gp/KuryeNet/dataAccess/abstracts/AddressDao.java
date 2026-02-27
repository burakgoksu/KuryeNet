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

import com.gp.KuryeNet.entities.concretes.Address;

@Repository
@Component
@Transactional
public interface AddressDao extends JpaRepository<Address,Integer>{
	
	List<Address> getByAddressTitle(String addressTitle);
	
	List<Address> getByPhoneNumber(String phoneNumber);
	
	List<Address> getByCity(String city);
	
	Address getByAddressId(int id);
	
	boolean existsByPhoneNumber(String phoneNumber);

	@Query(value = "SELECT * FROM addresses WHERE deleted = true", nativeQuery = true)
	List<Address> getDeleted();

	@Modifying
	@Query("UPDATE Address a SET a.deleted = false, a.deletedAt = null, a.deletedBy = null WHERE a.addressId = :id")
	int restoreById(@Param("id") int id);

	@Modifying
	@Query("UPDATE Address a SET a.deleted = true, a.deletedAt = :deletedAt, a.deletedBy = :deletedBy WHERE a.addressId = :id")
	int softDeleteById(@Param("id") int id, @Param("deletedAt") Instant deletedAt, @Param("deletedBy") String deletedBy);
	
}
