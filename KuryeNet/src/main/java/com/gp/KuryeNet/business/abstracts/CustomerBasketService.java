package com.gp.KuryeNet.business.abstracts;

import java.util.List;

import com.gp.KuryeNet.core.entities.User;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.entities.concretes.CustomerBasket;
import com.gp.KuryeNet.entities.concretes.Provider;

public interface CustomerBasketService {
	
	DataResult<List<CustomerBasket>> getAll();
	
	DataResult<List<CustomerBasket>> getAll(int pageNo, int pageSize);
	
	Result add(String customerEmail, String orderNumber);
	
	DataResult<CustomerBasket> getByOrder_OrderNumber(String orderNumber);
	
	DataResult<List<CustomerBasket>> getByCustomer_CustomerEmail(String customerEmail);

}
