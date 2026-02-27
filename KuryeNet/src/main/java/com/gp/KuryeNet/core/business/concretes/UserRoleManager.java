package com.gp.KuryeNet.core.business.concretes;

import java.util.List;
import java.time.Instant;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.check.AddressCheckService;
import com.gp.KuryeNet.core.business.abstracts.UserRoleService;
import com.gp.KuryeNet.core.dataAccess.UserRoleDao;
import com.gp.KuryeNet.core.entities.ApiError;
import com.gp.KuryeNet.core.entities.Role;
import com.gp.KuryeNet.core.entities.User;
import com.gp.KuryeNet.core.entities.UserRole;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorDataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;
import com.gp.KuryeNet.core.utulities.security.SecurityUtils;
import com.gp.KuryeNet.dataAccess.abstracts.AddressDao;
import com.gp.KuryeNet.entities.concretes.Address;

@Service
public class UserRoleManager implements UserRoleService{

	private UserRoleDao userRoleDao;


	@Autowired
	public UserRoleManager(UserRoleDao userRoleDao) {
		super();
		this.userRoleDao = userRoleDao;
	}
	
	@Async
	@Override
	public DataResult<List<UserRole>> getAll() {
		return new SuccessDataResult<List<UserRole>>(this.userRoleDao.findAll(),"UserRoles Data Listed");
	}

	@Override
	public DataResult<List<UserRole>> getAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1,pageSize);
		return new SuccessDataResult<List<UserRole>>(this.userRoleDao.findAll(pageable).getContent());
	}


	@Async
	@Transactional
	@Override
	public Result add(UserRole userRole) {
	    this.userRoleDao.save(userRole);
		return new SuccessResult(Msg.SAVED.get());
	}

	@Override
	public DataResult<List<UserRole>> getByRole_RoleName(String roleName) {
		return new SuccessDataResult<List<UserRole>>(this.userRoleDao.getByRole_RoleName(roleName));
		
	}

	@Override
	public DataResult<List<UserRole>> getByUser_Email(String userEmail) {
		return new SuccessDataResult<List<UserRole>>(this.userRoleDao.getByUser_Email(userEmail));
	}

	@Override
	public Result delete(int userRoleId) {
		int updated = userRoleDao.softDeleteById(userRoleId, Instant.now(), SecurityUtils.resolveCurrentUser());
		if (updated == 0) {
			return new ErrorResult(Msg.NOT_FOUND.get());
		}
		return new SuccessResult("userRole deleted");
	}

	@Override
	public Result restore(int userRoleId) {
		int updated = userRoleDao.restoreById(userRoleId);
		if (updated == 0) {
			return new ErrorResult(Msg.NOT_FOUND.get());
		}
		return new SuccessResult("userRole restored");
	}

	@Override
	public DataResult<List<UserRole>> getDeleted() {
		return new SuccessDataResult<List<UserRole>>(userRoleDao.getDeleted());
	}

}
