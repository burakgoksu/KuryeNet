package com.gp.KuryeNet.business.abstracts.check;

import java.util.Map;

public interface OrderCheckService extends BaseCheckService{

	Map<String, String> getErrors();
	
    void existsOrderById(int customerId);
    
    void existsByOrderNumber(String orderNumber);
    
    void availableOrder(int orderId);
    
    void distributionOrder(int orderId);
}

