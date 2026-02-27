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

import com.gp.KuryeNet.business.abstracts.UserService;
import com.gp.KuryeNet.core.entities.User;
import com.gp.KuryeNet.core.entities.UserDto;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.mapper.ResultMapper;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping(path = { "${api.base-path:/api}/users", "${api.versioned-base-path:/api/v1}/users" })
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
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
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.userService.getByEmail(email),
				user -> new UserDto(user.getId(), user.getName(), user.getSurname(), user.getEmail())));
	}

	@DeleteMapping("/delete")
	public ResponseEntity<?> delete(@RequestParam int userId) {
		return Utils.getResponseEntity(this.userService.delete(userId));
	}

	@PostMapping("/restore")
	public ResponseEntity<?> restore(@RequestParam int userId) {
		return Utils.getResponseEntity(this.userService.restore(userId));
	}

	@GetMapping("/deleted")
	public ResponseEntity<?> getDeleted() {
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.userService.getDeleted(),
				user -> new UserDto(user.getId(), user.getName(), user.getSurname(), user.getEmail())));
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
 

