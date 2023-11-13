package com.gp.KuryeNet.core.business.abstracts.check;

import java.util.Map;

import com.gp.KuryeNet.business.abstracts.check.BaseCheckService;

public interface AuthCheckService extends BaseCheckService{
	
	Map<String, String> getErrors();
	
	void existsUserById(int id);
	
	void existsByEmail(String email);
	
	void validEmail(String email);
	
	void validPassword(String password);

}
