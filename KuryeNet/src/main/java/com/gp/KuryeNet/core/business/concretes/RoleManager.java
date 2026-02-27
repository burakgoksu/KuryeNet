package com.gp.KuryeNet.core.business.concretes;

import java.util.List;
import java.time.Instant;

import jakarta.transaction.Transactional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.core.business.abstracts.RoleService;
import com.gp.KuryeNet.core.dataAccess.RoleDao;
import com.gp.KuryeNet.core.entities.Role;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;
import com.gp.KuryeNet.core.utulities.security.SecurityUtils;

@Service
public class RoleManager implements RoleService{
	
	private RoleDao roleDao;
	
	public RoleManager(RoleDao roleDao) {
		super();
		this.roleDao = roleDao;
	}


	@Async
	@Override
	public DataResult<List<Role>> getAll() {
		return new SuccessDataResult<List<Role>>(this.roleDao.findAll(),"Roles Data Listed");
	}

	@Override
	public DataResult<List<Role>> getAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1,pageSize);
		return new SuccessDataResult<List<Role>>(this.roleDao.findAll(pageable).getContent());
	}


	@Async
	@Transactional
	@Override
	public Result add(Role role) {
	    this.roleDao.save(role);
		return new SuccessResult(Msg.SAVED.get());
	}

	@Override
	public DataResult<Role> getByRoleId(int roleId) {
		return new SuccessDataResult<Role>(this.roleDao.getByRoleId(roleId));
		
	}

	@Override
	public DataResult<Role> getByRoleName(String roleName) {
		return new SuccessDataResult<Role>(this.roleDao.getByRoleName(roleName));
	}

	@Override
	public Result delete(int roleId) {
		int updated = roleDao.softDeleteById(roleId, Instant.now(), SecurityUtils.resolveCurrentUser());
		if (updated == 0) {
			return new ErrorResult(Msg.NOT_FOUND.get());
		}
		return new SuccessResult("role deleted");
	}

	@Override
	public Result restore(int roleId) {
		int updated = roleDao.restoreById(roleId);
		if (updated == 0) {
			return new ErrorResult(Msg.NOT_FOUND.get());
		}
		return new SuccessResult("role restored");
	}

	@Override
	public DataResult<List<Role>> getDeleted() {
		return new SuccessDataResult<List<Role>>(roleDao.getDeleted());
	}

}
