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

import com.gp.KuryeNet.core.business.abstracts.RoleService;
import com.gp.KuryeNet.core.business.abstracts.UserRoleService;
import com.gp.KuryeNet.core.entities.Role;
import com.gp.KuryeNet.core.entities.UserRole;
import com.gp.KuryeNet.core.utulities.Util.Utils;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/roles")
public class RoleControllers {

	private RoleService roleService;
	
	@Autowired
	public RoleControllers(RoleService roleService) {
		super();
		this.roleService = roleService;
	}

	@PostMapping("/add")
	public Mono<ResponseEntity<?>> add(@Valid @RequestBody Role role) {
		return Mono.fromCallable(()->{
			return this.roleService.add(role);
		}).map(result -> Utils.getResponseEntity(result));
	}
	
	@GetMapping("/getByRoleId")
	public ResponseEntity<?> getByRoleId(@Valid @RequestParam int roleId) {
		return Utils.getResponseEntity(this.roleService.getByRoleId(roleId));
	}
	
	@GetMapping("/getByRoleName")
	public ResponseEntity<?> getByRoleName(@Valid @RequestParam String roleName) {
		return Utils.getResponseEntity(this.roleService.getByRoleName(roleName));
	}
	
}
