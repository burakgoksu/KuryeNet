package com.gp.KuryeNet.business.concretes;

import jakarta.transaction.Transactional;
import java.time.Instant;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.UserService;
import com.gp.KuryeNet.core.dataAccess.UserDao;
import com.gp.KuryeNet.core.entities.User;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;
import com.gp.KuryeNet.core.utulities.security.SecurityUtils;

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

	@Override
	public Result delete(int userId) {
		int updated = userDao.softDeleteById(userId, Instant.now(), SecurityUtils.resolveCurrentUser());
		if (updated == 0) {
			return new ErrorResult(Msg.NOT_FOUND.get());
		}
		return new SuccessResult("user deleted");
	}

	@Override
	public Result restore(int userId) {
		int updated = userDao.restoreById(userId);
		if (updated == 0) {
			return new ErrorResult(Msg.NOT_FOUND.get());
		}
		return new SuccessResult("user restored");
	}

	@Override
	public DataResult<List<User>> getDeleted() {
		return new SuccessDataResult<List<User>>(userDao.getDeleted());
	}

}
