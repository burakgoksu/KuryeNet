package com.gp.KuryeNet.core.business.concretes;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.core.business.abstracts.AuthService;
import com.gp.KuryeNet.core.business.abstracts.check.AuthCheckService;
import com.gp.KuryeNet.core.dataAccess.UserDao;
import com.gp.KuryeNet.core.entities.ApiError;
import com.gp.KuryeNet.core.entities.SignupDto;
import com.gp.KuryeNet.core.entities.User;
import com.gp.KuryeNet.core.entities.UserDto;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorDataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;

@Service
public class AuthManager implements AuthService{

	private UserDao userDao;
	private AuthCheckService authCheckService;

	@Autowired
	public AuthManager(UserDao userDao,AuthCheckService authCheckService) {
		super();
		this.userDao = userDao;
		this.authCheckService = authCheckService;
	}

	@Async
	@Transactional
	@Override
	public Result createUser(SignupDto signupDto) {
		authCheckService.existsByEmail(signupDto.getEmail());
		authCheckService.validEmail(signupDto.getEmail());
		authCheckService.validPassword(signupDto.getPassword());
		ErrorDataResult<ApiError> errors= Utils.getErrorsIfExist(authCheckService);
		if(errors!=null) return errors;
		
	 	User user = new User();
        user.setName(signupDto.getName());
        user.setSurname(signupDto.getSurname());
        user.setEmail(signupDto.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(signupDto.getPassword()));
        User createdUser = userDao.save(user);
        UserDto userDto = new UserDto();
        userDto.setId(createdUser.getId());
        userDto.setEmail(createdUser.getEmail());
        userDto.setName(createdUser.getName());
        userDto.setSurname(createdUser.getSurname());
        return new SuccessDataResult<UserDto>(userDto,"User created Successfuly");
	}
}
