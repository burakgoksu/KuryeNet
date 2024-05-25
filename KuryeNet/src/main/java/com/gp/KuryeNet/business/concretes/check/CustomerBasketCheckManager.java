package com.gp.KuryeNet.business.concretes.check;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.check.CustomerBasketCheckService;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.dataAccess.abstracts.CustomerBasketDao;
import com.gp.KuryeNet.dataAccess.abstracts.CustomerDao;

@Service
public class CustomerBasketCheckManager extends BaseCheckManager implements CustomerBasketCheckService{

private CustomerBasketDao customerBasketDao;
	
	@Autowired
	public CustomerBasketCheckManager(CustomerBasketDao customerBasketDao) {
		super();
		this.customerBasketDao = customerBasketDao;
	}

	@Override
	public void existsOrderByNumber(String orderNumber) {
		if(customerBasketDao.existsByOrder_OrderNumber(orderNumber))
			errors.put("Order Number", Msg.EXISTS.get()+ " or false");
		
	}
	
	@Override
	public void existsOrderByNumber2(String orderNumber) {
		if(!customerBasketDao.existsByOrder_OrderNumber(orderNumber))
			errors.put("Order Number", Msg.NOT_FOUND.get());
		
	}


}
