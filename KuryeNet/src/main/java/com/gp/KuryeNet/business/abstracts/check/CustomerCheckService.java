package com.gp.KuryeNet.business.abstracts.check;

import java.util.Map;

public interface CustomerCheckService extends BaseCheckService{

	Map<String, String> getErrors();
	
    void existsCustomerById(int customerId);
    
    void existsByCustomerEmail(String customerEmail);
    
    void validEmail(String customerEmail);
}
