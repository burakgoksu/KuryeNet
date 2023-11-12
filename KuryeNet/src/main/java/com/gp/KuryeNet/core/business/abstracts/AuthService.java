package com.gp.KuryeNet.core.business.abstracts;

import com.gp.KuryeNet.core.entities.SignupDto;
import com.gp.KuryeNet.core.entities.UserDto;

public interface AuthService {
	 UserDto createUser(SignupDto signupDto);
}
