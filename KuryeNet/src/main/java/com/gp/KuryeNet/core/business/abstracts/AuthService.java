package com.gp.KuryeNet.core.business.abstracts;

import com.gp.KuryeNet.core.entities.SignupDto;
import com.gp.KuryeNet.core.entities.UserDto;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;

public interface AuthService {
	 Result createUser(SignupDto signupDto);
}
