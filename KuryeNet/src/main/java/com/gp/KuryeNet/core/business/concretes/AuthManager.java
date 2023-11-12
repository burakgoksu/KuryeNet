package com.gp.KuryeNet.core.business.concretes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.core.business.abstracts.AuthService;
import com.gp.KuryeNet.core.dataAccess.UserDao;
import com.gp.KuryeNet.core.entities.SignupDto;
import com.gp.KuryeNet.core.entities.User;
import com.gp.KuryeNet.core.entities.UserDto;

@Service
public class AuthManager implements AuthService{

	private UserDao userDao;

	@Autowired
	public AuthManager(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Override
	public UserDto createUser(SignupDto signupDto) {
		 	User user = new User();
	        user.setName(signupDto.getName());
	        user.setEmail(signupDto.getEmail());
	        user.setPassword(new BCryptPasswordEncoder().encode(signupDto.getPassword()));
	        User createdUser = userDao.save(user);
	        UserDto userDto = new UserDto();
	        userDto.setId(createdUser.getId());
	        userDto.setEmail(createdUser.getEmail());
	        userDto.setName(createdUser.getName());
	        return userDto;
	}
}
