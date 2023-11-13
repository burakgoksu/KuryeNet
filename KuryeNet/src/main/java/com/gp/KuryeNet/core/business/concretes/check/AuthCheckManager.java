package com.gp.KuryeNet.core.business.concretes.check;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.concretes.check.BaseCheckManager;
import com.gp.KuryeNet.core.business.abstracts.AuthService;
import com.gp.KuryeNet.core.business.abstracts.check.AuthCheckService;
import com.gp.KuryeNet.core.dataAccess.UserDao;
import com.gp.KuryeNet.core.utulities.Util.CheckUtils;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.core.utulities.exception.exceptions.EntityNotExistsException;

import lombok.SneakyThrows;

@Service
public class AuthCheckManager extends BaseCheckManager implements AuthCheckService{

	private UserDao userDao;

	@Autowired
	public AuthCheckManager(UserDao userDao) {
		super();
		this.userDao = userDao;
	}

	@SneakyThrows
	@Override
	public void existsUserById(int id) {
		if(CheckUtils.notExistsById(userDao, id))
			throw new EntityNotExistsException(Msg.NOT_EXIST.get("User"));
		
	}

	@Override
	public void existsByEmail(String email) {
		if(userDao.existsByEmail(email))
			errors.put("UserEmail", Msg.IS_IN_USE.get());
		
	}

	@Override
	public void validEmail(String email) {
		if(CheckUtils.invalidEmail(email))
			errors.put("UserEmail", Msg.IS_NOT_VALID.get());		
	}

	@Override
	public void validPassword(String password) {
		if(CheckUtils.invalidPassword(password))
			errors.put("UserPassword", Msg.IS_NOT_VALID.get());
		
	}

}
