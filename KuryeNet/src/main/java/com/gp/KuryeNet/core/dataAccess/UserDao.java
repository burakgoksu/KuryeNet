package com.gp.KuryeNet.core.dataAccess;

import java.util.Optional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;
import java.time.Instant;

import com.gp.KuryeNet.core.entities.User;

@Repository
@Component
public interface UserDao extends JpaRepository<User, Integer>{
	User getByEmail(String email);
		
	User findFirstByEmail(String email);
	
	boolean existsByEmail(String Email);

	@Query(value = "SELECT * FROM users WHERE deleted = true", nativeQuery = true)
	java.util.List<User> getDeleted();

	@Modifying
	@Query("UPDATE User u SET u.deleted = false, u.deletedAt = null, u.deletedBy = null WHERE u.id = :id")
	int restoreById(@Param("id") int id);

	@Modifying
	@Query("UPDATE User u SET u.deleted = true, u.deletedAt = :deletedAt, u.deletedBy = :deletedBy WHERE u.id = :id")
	int softDeleteById(@Param("id") int id, @Param("deletedAt") Instant deletedAt, @Param("deletedBy") String deletedBy);
	
}
