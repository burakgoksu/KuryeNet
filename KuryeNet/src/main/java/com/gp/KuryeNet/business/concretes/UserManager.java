package com.gp.KuryeNet.business.concretes;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.UserService;
import com.gp.KuryeNet.core.dataAccess.UserDao;
import com.gp.KuryeNet.core.entities.User;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;

@Service
public class UserManager implements UserService{
	
	private UserDao userDao;
	
	@Autowired
	public UserManager(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@Async
	@Transactional
	@Override
	public Result add(User user) {
		this.userDao.save(user);
		return new SuccessResult("User added successfuly");
	}

	@Override
	public DataResult<User> getByEmail(String email) {
		return new SuccessDataResult<User>(this.userDao.getByEmail(email),"User is found");
	}

}
