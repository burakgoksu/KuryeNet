package com.gp.KuryeNet.dataAccess.abstracts;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.entities.concretes.CustomerBasket;

@Repository
@Component
@Transactional
public interface CustomerBasketDao extends JpaRepository<CustomerBasket,Integer>{
	
	CustomerBasket getByOrder_OrderNumber(String orderNumber);
	
	List<CustomerBasket> getByCustomer_CustomerEmail(String customerEmail);
	
	boolean existsByOrder_OrderNumber(String orderNumber);

}
