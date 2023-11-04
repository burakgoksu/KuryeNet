package com.gp.KuryeNet.business.abstracts.check;

import java.util.Map;

public interface AddressCheckService extends BaseCheckService{
	
	Map<String, String> getErrors();
	
    void existsAddressById(int addressId);
    
    void existsPhoneNumber(String phoneNumber);
    
    void validPhoneNumber(String phoneNumber);

	
}
