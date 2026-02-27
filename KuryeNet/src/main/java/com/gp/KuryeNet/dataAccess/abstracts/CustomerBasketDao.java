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

import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.entities.concretes.CustomerBasket;

@Repository
@Component
@Transactional
public interface CustomerBasketDao extends JpaRepository<CustomerBasket,Integer>{
	
	CustomerBasket getByOrder_OrderNumber(String orderNumber);
	
	List<CustomerBasket> getByCustomer_CustomerEmail(String customerEmail);
	
	boolean existsByOrder_OrderNumber(String orderNumber);

	@Query(value = "SELECT * FROM customers_baskets WHERE deleted = true", nativeQuery = true)
	List<CustomerBasket> getDeleted();

	@Modifying
	@Query("UPDATE CustomerBasket cb SET cb.deleted = false, cb.deletedAt = null, cb.deletedBy = null WHERE cb.customerBasketId = :id")
	int restoreById(@Param("id") int id);

	@Modifying
	@Query("UPDATE CustomerBasket cb SET cb.deleted = true, cb.deletedAt = :deletedAt, cb.deletedBy = :deletedBy WHERE cb.order.orderNumber = :orderNumber")
	int softDeleteByOrderNumber(@Param("orderNumber") String orderNumber, @Param("deletedAt") Instant deletedAt, @Param("deletedBy") String deletedBy);
}
