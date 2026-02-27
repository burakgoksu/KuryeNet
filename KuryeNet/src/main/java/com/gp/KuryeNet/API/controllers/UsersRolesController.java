package com.gp.KuryeNet.API.controllers;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.core.business.abstracts.UserRoleService;
import com.gp.KuryeNet.core.entities.UserRole;
import com.gp.KuryeNet.core.entities.UserRoleDto;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.mapper.ResultMapper;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = { "${api.base-path:/api}/usersroles", "${api.versioned-base-path:/api/v1}/usersroles" })
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class UsersRolesController {

	private UserRoleService userRoleService;
	
	@Autowired
	public UsersRolesController(UserRoleService userRoleService) {
		super();
		this.userRoleService = userRoleService;
	}

	@PostMapping("/add")
	public Mono<ResponseEntity<?>> add(@Valid @RequestBody UserRole userRole) {
		return Mono.fromCallable(()->{
			return this.userRoleService.add(userRole);
		}).map(result -> Utils.getResponseEntity(result));
	}
	
	@GetMapping("/getByUserEmail")
	public ResponseEntity<?> getByUserEmail(@Valid @RequestParam String userEmail) {
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.userRoleService.getByUser_Email(userEmail), UserRoleDto::fromEntity));
	}
	
	@GetMapping("/getByRoleName")
	public ResponseEntity<?> getByRoleName(@Valid @RequestParam String roleName) {
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.userRoleService.getByRole_RoleName(roleName), UserRoleDto::fromEntity));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam int userRoleId) {
		return Utils.getResponseEntity(this.userRoleService.delete(userRoleId));
	}

	@PostMapping("/restore")
	public ResponseEntity<?> restore(@RequestParam int userRoleId) {
		return Utils.getResponseEntity(this.userRoleService.restore(userRoleId));
	}

	@GetMapping("/deleted")
	public ResponseEntity<?> getDeleted() {
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.userRoleService.getDeleted(), UserRoleDto::fromEntity));
	}
	
}

