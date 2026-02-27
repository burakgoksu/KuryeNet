package com.gp.KuryeNet.API.controllers;

import java.util.List;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;


import com.gp.KuryeNet.business.abstracts.CourierService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.mapper.ResultMapper;
import com.gp.KuryeNet.core.utulities.jwt.JwtUtil;
import com.gp.KuryeNet.entities.dtos.CourierDto;
import com.gp.KuryeNet.entities.dtos.CourierWithVehicleDto;
import com.gp.KuryeNet.entities.dtos.requests.CourierCreateRequest;

import reactor.core.publisher.Mono;


@RestController
@Validated
@RequestMapping(path = { "${api.base-path:/api}/couriers", "${api.versioned-base-path:/api/v1}/couriers" })
public class CouriersController {

	private CourierService courierService;
	private JwtUtil jwtUtil;

	@Autowired
	public CouriersController(CourierService courierService, JwtUtil jwtUtil) {
		super();
		this.courierService = courierService;
		this.jwtUtil = jwtUtil;
	}
	
	@GetMapping("/getall")
	public Mono<ResponseEntity<?>> getAll(){
		return Mono.fromCallable(()->{
			return this.courierService.getAll(); 
		}).map(result -> Utils.getResponseEntity(ResultMapper.mapListIfSuccess(result, CourierDto::fromEntity)));
		
	}
	
	@GetMapping("/getallByPage")
	public ResponseEntity<?> getAll(@RequestParam @Min(0) int pageNo,@RequestParam @Min(1) @Max(200) int pageSize){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.courierService.getAll(pageNo,pageSize), CourierDto::fromEntity));
		
	}
	
	@GetMapping("/getAllSortedByCourierName")
	public ResponseEntity<?> getAllSortedByCourierName(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.courierService.getAllSortedByCourierName(), CourierDto::fromEntity));
		
	}
	
	@PostMapping("/add")
	public Mono<ResponseEntity<?>> add(@Valid @RequestBody CourierCreateRequest courierRequest){
		return Mono.fromCallable(()->{
			return this.courierService.add(courierRequest.toEntity());
		}).map(result -> Utils.getResponseEntity(result));
		
	}
	
	@GetMapping("/getByCourierNameAndSurname")
	public ResponseEntity<?> getByCourierNameAndCourierSurname(@RequestParam("name") String name, @RequestParam("surname") String surname){
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.courierService.getByCourierNameAndCourierSurname(name, surname), CourierDto::fromEntity));
	}
	
	@GetMapping("/getByCourierIdentityNumber")
	public ResponseEntity<?> getByCourierIdentityNumber(@RequestParam String courierIdentityNumber){
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.courierService.getByCourierIdentityNumber(courierIdentityNumber), CourierDto::fromEntity));
	}
	
	@GetMapping("/getByCourierEmail")
	public ResponseEntity<?> getByCourierEmail(HttpServletRequest request){
		String token = jwtUtil.extractTokenFromRequest(request);
	    String courierEmail = jwtUtil.extractUsername(token);

	    return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.courierService.getByCourierEmail(courierEmail), CourierDto::fromEntity));
	}
	
	@GetMapping("/existsByCourierEmail")
	public ResponseEntity<?> existsByCourierEmail(HttpServletRequest request){
		String token = jwtUtil.extractTokenFromRequest(request);
	    String courierEmail = jwtUtil.extractUsername(token);

	    return Utils.getResponseEntity(this.courierService.existsByCourierEmail(courierEmail));
	}
	
	@GetMapping("/getByCourierCity")
	public ResponseEntity<?> getByCourierAddress_City(@RequestParam String city){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.courierService.getByCourierAddress_City(city), CourierDto::fromEntity));
	}
	
	@GetMapping("/getCourierWithVehicleDetails")
	public ResponseEntity<?> getCourierWithVehicleDetails(){
		return Utils.getResponseEntity(this.courierService.getCourierWithVehicleDetails());
	}
	
	@GetMapping("/getCourierWithOrderDetails")
	public Mono<ResponseEntity<?>> getCourierWithOrderDetails(@Valid @RequestParam String orderNumber){
		return Mono.fromCallable(()->{
			return this.courierService.getCourierWithOrderDetails(orderNumber);
		}).map(result -> Utils.getResponseEntity(result));
	}
	
	@GetMapping("/getById")
	public ResponseEntity<?> getByCourierId(@Valid @RequestParam int addressId){
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.courierService.getByCourierId(addressId), CourierDto::fromEntity)); 
	}
	
	@GetMapping("/getByCourierIdWithOrderId")
	public ResponseEntity<?> getByCourierIdWithOrderId(@Valid @RequestParam int orderId){
		return Utils.getResponseEntity(this.courierService.getByCourierIdWithOrderId(orderId)); 
	}
	
	@PostMapping("/startOrder")
    public Mono<ResponseEntity<?>> startOrder(@Valid @RequestParam int orderId, HttpServletRequest request) {
        return Mono.fromCallable(() -> {
            String token = jwtUtil.extractTokenFromRequest(request);
            String courierEmail = jwtUtil.extractUsername(token);
            
            return this.courierService.startOrder(orderId, courierEmail);
        }).map(result -> Utils.getResponseEntity(result));
    }
	
	@PostMapping("/endOrder")
	public Mono<ResponseEntity<?>> endOrder(@Valid @RequestParam int orderId, HttpServletRequest request){
		return Mono.fromCallable(()->{
			String token = jwtUtil.extractTokenFromRequest(request);
		    String courierEmail = jwtUtil.extractUsername(token);
		    
			return this.courierService.endOrder(orderId,courierEmail);
		}).map(result -> Utils.getResponseEntity(result));

	}
	
	@PutMapping("/updateCourierCoordinates")
	public Mono<ResponseEntity<?>> updateCourierCoordinates(HttpServletRequest request, @RequestParam double latitude, @RequestParam double longitude){
		return Mono.fromCallable(()->{
		    String token = jwtUtil.extractTokenFromRequest(request);
		    String courierEmail = jwtUtil.extractUsername(token);
		
		    return this.courierService.updateCourierCoordinates(courierEmail,latitude,longitude);
		}).map(result -> Utils.getResponseEntity(result));
	}
	
	@PutMapping("/updateCourierCoordinatesSimulate")
	public Mono<ResponseEntity<?>> updateCourierCoordinatesSimulate(HttpServletRequest request){
		return Mono.fromCallable(()->{
		    String token = jwtUtil.extractTokenFromRequest(request);
		    String courierEmail = jwtUtil.extractUsername(token);
		
		    return this.courierService.updateCourierCoordinatesSimulate(courierEmail);
		}).map(result -> Utils.getResponseEntity(result));
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> delete(@RequestParam int courierId){
		return Utils.getResponseEntity(this.courierService.delete(courierId));
	}

	@PostMapping("/restore")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> restore(@RequestParam int courierId){
		return Utils.getResponseEntity(this.courierService.restore(courierId));
	}

	@GetMapping("/deleted")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getDeleted(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.courierService.getDeleted(), CourierDto::fromEntity));
	}
	
	
}

