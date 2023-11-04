package com.gp.KuryeNet.business.abstracts.check;

import java.util.Map;

public interface ProviderCheckService extends BaseCheckService{
	
	Map<String, String> getErrors();
	
    void existsProviderById(int providerId);
    
    void existsByProviderMersisNo(String providerMersisNo);
    
    void validProviderMersisNo(String providerMersisNo);

}
