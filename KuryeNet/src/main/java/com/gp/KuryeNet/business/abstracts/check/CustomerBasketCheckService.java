package com.gp.KuryeNet.business.abstracts.check;

import java.util.Map;

public interface CustomerBasketCheckService extends BaseCheckService{
	
	Map<String, String> getErrors();
	
    void existsOrderByNumber(String orderNumber);

}
