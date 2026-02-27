package com.gp.KuryeNet.core.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRoleDto {

	private int userRoleId;
	private Integer userId;
	private Integer roleId;

	public static UserRoleDto fromEntity(UserRole userRole) {
		if (userRole == null) {
			return null;
		}
		Integer userId = userRole.getUser() != null ? userRole.getUser().getId() : null;
		Integer roleId = userRole.getRole() != null ? userRole.getRole().getRoleId() : null;
		return new UserRoleDto(userRole.getUserRoleId(), userId, roleId);
	}
}
