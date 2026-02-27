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

import com.gp.KuryeNet.entities.concretes.Customer;


@Repository
@Component
@Transactional
public interface CustomerDao extends JpaRepository<Customer,Integer>{

	Customer getByCustomerId(int customerId);
	
	Customer getByCustomerNameAndCustomerSurname(String customerName,String customerSurname);
	
	Customer getByCustomerEmail(String customerEmail);
	
	Customer getByCustomerAddress_AddressId(int addressId);
	
	List<Customer> getByCustomerAddress_City(String city);
	
	boolean existsByCustomerEmail(String customerEmail);

	@Query(value = "SELECT * FROM customers WHERE deleted = true", nativeQuery = true)
	List<Customer> getDeleted();

	@Modifying
	@Query("UPDATE Customer c SET c.deleted = false, c.deletedAt = null, c.deletedBy = null WHERE c.customerId = :id")
	int restoreById(@Param("id") int id);

	@Modifying
	@Query("UPDATE Customer c SET c.deleted = true, c.deletedAt = :deletedAt, c.deletedBy = :deletedBy WHERE c.customerId = :id")
	int softDeleteById(@Param("id") int id, @Param("deletedAt") Instant deletedAt, @Param("deletedBy") String deletedBy);
}
