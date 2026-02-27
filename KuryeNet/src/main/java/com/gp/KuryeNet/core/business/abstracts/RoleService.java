package com.gp.KuryeNet.core.business.abstracts;

import java.util.List;

import com.gp.KuryeNet.core.entities.Role;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;

public interface RoleService {
	
	DataResult<List<Role>> getAll();
	
	DataResult<List<Role>> getAll(int pageNo, int pageSize);
	
	Result add(Role role);

	Result delete(int roleId);

	Result restore(int roleId);

	DataResult<List<Role>> getDeleted();
	
	DataResult<Role> getByRoleId(int roleId);
	
	DataResult<Role> getByRoleName(String roleName);

}
