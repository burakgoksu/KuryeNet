package com.gp.KuryeNet.API.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.core.business.abstracts.UserRoleService;
import com.gp.KuryeNet.core.entities.UserRole;
import com.gp.KuryeNet.core.utulities.Util.Utils;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/usersroles")
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
		return Utils.getResponseEntity(this.userRoleService.getByUser_Email(userEmail));
	}
	
	@GetMapping("/getByRoleName")
	public ResponseEntity<?> getByRoleName(@Valid @RequestParam String roleName) {
		return Utils.getResponseEntity(this.userRoleService.getByRole_RoleName(roleName));
	}
	
}
