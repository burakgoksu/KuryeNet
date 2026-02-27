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
import com.gp.KuryeNet.core.entities.User;
import com.gp.KuryeNet.core.entities.UserRole;

@Repository
@Component
public interface UserRoleDao extends JpaRepository<UserRole, Integer>{
	
	List<UserRole> getByUser_Email(String userEmail);
	
	List<UserRole> getByRole_RoleName(String roleName);

	@Query(value = "SELECT * FROM users_roles WHERE deleted = true", nativeQuery = true)
	List<UserRole> getDeleted();

	@Modifying
	@Query("UPDATE UserRole ur SET ur.deleted = false, ur.deletedAt = null, ur.deletedBy = null WHERE ur.userRoleId = :id")
	int restoreById(@Param("id") int id);

	@Modifying
	@Query("UPDATE UserRole ur SET ur.deleted = true, ur.deletedAt = :deletedAt, ur.deletedBy = :deletedBy WHERE ur.userRoleId = :id")
	int softDeleteById(@Param("id") int id, @Param("deletedAt") Instant deletedAt, @Param("deletedBy") String deletedBy);

}
