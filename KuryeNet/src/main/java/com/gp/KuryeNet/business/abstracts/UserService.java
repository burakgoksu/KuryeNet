package com.gp.KuryeNet.business.abstracts;

import com.gp.KuryeNet.core.entities.User;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;

public interface UserService {
	
	Result add(User user);
	
	DataResult<User> getByEmail(String email);
}
