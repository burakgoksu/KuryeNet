package com.gp.KuryeNet.API.controllers;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gp.KuryeNet.business.abstracts.UserService;
import com.gp.KuryeNet.core.entities.User;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorDataResult;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/users")
public class UsersController {
	
	private UserService userService;
	
	@Autowired
	public UsersController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PostMapping("/add")
	public Mono<ResponseEntity<?>> add(@Valid @RequestBody User user) {
		return Mono.fromCallable(()->{
			return this.userService.add(user);
		}).map(result -> Utils.getResponseEntity(result));
	}
	
	@GetMapping("/getByEmail")
	public ResponseEntity<?> getByEmail(@Valid @RequestParam String email) {
		return Utils.getResponseEntity(this.userService.getByEmail(email));
	}
	
	
//	@ExceptionHandler(MethodArgumentNotValidException.class)
//	@ResponseStatus(HttpStatus.BAD_REQUEST)
//	public ErrorDataResult<Object> handleValidationException(MethodArgumentNotValidException exceptions){
//		Map<String,String> validationErrors = new HashMap<String, String>();
//		
//		for(FieldError fieldError : exceptions.getBindingResult().getFieldErrors()) {
//			validationErrors.put(fieldError.getField(),fieldError.getDefaultMessage());
//		}
//		ErrorDataResult<Object> errors = new ErrorDataResult<Object>(validationErrors,"Validation Errors");
//		return errors;
//		
//	}
	
}
 