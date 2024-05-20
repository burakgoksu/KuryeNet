package com.gp.KuryeNet.core.dataAccess;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.gp.KuryeNet.core.entities.Role;
import com.gp.KuryeNet.core.entities.User;
import com.gp.KuryeNet.core.entities.UserRole;

@Repository
@Component
public interface UserRoleDao extends JpaRepository<UserRole, Integer>{
	
	List<UserRole> getByUser_Email(String userEmail);
	
	List<UserRole> getByRole_RoleName(String roleName);

}
