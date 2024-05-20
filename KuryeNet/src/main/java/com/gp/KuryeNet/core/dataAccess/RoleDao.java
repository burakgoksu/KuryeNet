package com.gp.KuryeNet.core.dataAccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.gp.KuryeNet.core.entities.Role;
import com.gp.KuryeNet.core.entities.UserRole;

@Repository
@Component
public interface RoleDao extends JpaRepository<Role, Integer>{
	
	Role getByRoleId(int roleId);
	
	Role getByRoleName(String roleName);

}
