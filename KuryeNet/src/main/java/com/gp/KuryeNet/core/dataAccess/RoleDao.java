package com.gp.KuryeNet.core.dataAccess;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.time.Instant;

import com.gp.KuryeNet.core.entities.Role;
import com.gp.KuryeNet.core.entities.UserRole;

@Repository
@Component
public interface RoleDao extends JpaRepository<Role, Integer>{
	
	Role getByRoleId(int roleId);
	
	Role getByRoleName(String roleName);

	@Query(value = "SELECT * FROM roles WHERE deleted = true", nativeQuery = true)
	List<Role> getDeleted();

	@Modifying
	@Query("UPDATE Role r SET r.deleted = false, r.deletedAt = null, r.deletedBy = null WHERE r.roleId = :id")
	int restoreById(@Param("id") int id);

	@Modifying
	@Query("UPDATE Role r SET r.deleted = true, r.deletedAt = :deletedAt, r.deletedBy = :deletedBy WHERE r.roleId = :id")
	int softDeleteById(@Param("id") int id, @Param("deletedAt") Instant deletedAt, @Param("deletedBy") String deletedBy);

}
