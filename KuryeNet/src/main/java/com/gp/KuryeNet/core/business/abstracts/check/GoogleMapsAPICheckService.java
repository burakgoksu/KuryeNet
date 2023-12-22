package com.gp.KuryeNet.core.business.abstracts.check;

import java.util.Map;

import com.gp.KuryeNet.business.abstracts.check.BaseCheckService;

public interface GoogleMapsAPICheckService extends BaseCheckService{

	Map<String, String> getErrors();
	
	void isOrderInDistribution(int orderId);
	
	void isCourierInDistribution(String courierEmail);
	
}
