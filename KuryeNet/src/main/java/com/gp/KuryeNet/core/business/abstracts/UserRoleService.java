package com.gp.KuryeNet.core.business.abstracts;

import java.util.List;

import com.gp.KuryeNet.core.entities.Role;
import com.gp.KuryeNet.core.entities.User;
import com.gp.KuryeNet.core.entities.UserRole;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;

public interface UserRoleService {

	DataResult<List<UserRole>> getAll();
	
	DataResult<List<UserRole>> getAll(int pageNo, int pageSize);
	
	Result add(UserRole userRole);
	
	DataResult<List<UserRole>> getByRole_RoleName(String roleName);
	
	DataResult<List<UserRole>> getByUser_Email(String userEmail);
	
}
