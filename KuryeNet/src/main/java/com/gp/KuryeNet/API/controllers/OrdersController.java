package com.gp.KuryeNet.API.controllers;

import com.gp.KuryeNet.business.abstracts.OrderService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.mapper.ResultMapper;
import com.gp.KuryeNet.entities.dtos.OrderDto;
import com.gp.KuryeNet.entities.dtos.OrderWithAddressDto;
import com.gp.KuryeNet.entities.dtos.OrderWithCourierDto;
import com.gp.KuryeNet.entities.dtos.requests.OrderCreateRequest;

import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;

@RestController
@Validated
@RequestMapping(path = { "${api.base-path:/api}/orders", "${api.versioned-base-path:/api/v1}/orders" })
public class OrdersController {
	
	private OrderService orderService;
	
	@Autowired
	public OrdersController(OrderService orderService) {
		super();
		this.orderService = orderService;
	}

	@GetMapping("/getall")
	public Mono<ResponseEntity<?>> getAll(){
		return Mono.fromCallable(()->{
			return this.orderService.getAll();
		}).map(result -> Utils.getResponseEntity(ResultMapper.mapListIfSuccess(result, OrderDto::fromEntity)));
		
	}
	
	@GetMapping("/getallByPage")
	public ResponseEntity<?> getAll(@RequestParam @Min(0) int pageNo,@RequestParam @Min(1) @Max(200) int pageSize){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.orderService.getAll(pageNo,pageSize), OrderDto::fromEntity));
		
	}
	
	@GetMapping("/getAllSortedByOrderDate")
	public ResponseEntity<?> getAllSortedByOrderDate(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.orderService.getAllSortedByOrderDate(), OrderDto::fromEntity));
		
	}
	
	@PostMapping("/add")
	public Mono<ResponseEntity<?>> add(@Valid @RequestBody OrderCreateRequest orderRequest){
		return Mono.fromCallable(()->{
			return this.orderService.add(orderRequest.toEntity());
		}).map(result -> Utils.getResponseEntity(result));
		
	}
	
	@GetMapping("/getByOrderNumber")
	public ResponseEntity<?> getByOrderNumber(@RequestParam String orderNumber){
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.orderService.getByOrderNumber(orderNumber), OrderDto::fromEntity));
	}
	
	@GetMapping("/getByOrderHourAndOrderStatus")
	public ResponseEntity<?> getByOrderHourAndOrderStatus(@RequestParam("orderHour") int orderHour,@RequestParam("orderStatus") int orderStatus ){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.orderService.getByOrderHourAndOrderStatus(orderHour, orderStatus), OrderDto::fromEntity));
	}
	
	@GetMapping("/getByOrderStatus")
	public ResponseEntity<?> getByOrderStatus(@RequestParam int orderStatus){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.orderService.getByOrderStatus(orderStatus), OrderDto::fromEntity));
	}
	
	@GetMapping("/getByOrderType")
	public ResponseEntity<?> getByOrderType(@RequestParam String orderType){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.orderService.getByOrderType(orderType), OrderDto::fromEntity));
	}
	
	@GetMapping("/getByOrderDate")
	public ResponseEntity<?> getByOrderDate(@RequestParam Date orderDate){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.orderService.getByOrderDate(orderDate), OrderDto::fromEntity));
	}
	
	@GetMapping("/getByCourierIdIn")
	public ResponseEntity<?> getByCourierIdIn(@RequestParam List<Integer> couriersId){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.orderService.getByCourier_CourierIdIn(couriersId), OrderDto::fromEntity));
	}
	
	@GetMapping("/getByOrderWithCourierDetails")
	public ResponseEntity<?> getByOrderWithCourierDetails(){
		return Utils.getResponseEntity(this.orderService.getOrderWithCourierDetails());
	}
	
	@GetMapping("/getByOrderAddressId")
	public ResponseEntity<?> getByOrderAddress_AddressId(@RequestParam int addressId){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.orderService.getByOrderAddress_AddressId(addressId), OrderDto::fromEntity));
	}
	
	@GetMapping("/getByOrderWithAddressDetails")
	public ResponseEntity<?> getByOrderWithAddressDetails(){
		return Utils.getResponseEntity(this.orderService.getOrderWithAddressDetails());
	}
	
	@GetMapping("/getByOrderId")
	public ResponseEntity<?> getByOrderId(@RequestParam int orderId){
		return Utils.getResponseEntity(ResultMapper.mapIfSuccess(this.orderService.getByOrderId(orderId), OrderDto::fromEntity));
		
	}

	@DeleteMapping("/delete")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> delete(@RequestParam int orderId){
		return Utils.getResponseEntity(this.orderService.delete(orderId));
	}

	@PostMapping("/restore")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> restore(@RequestParam int orderId){
		return Utils.getResponseEntity(this.orderService.restore(orderId));
	}

	@GetMapping("/deleted")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<?> getDeleted(){
		return Utils.getResponseEntity(ResultMapper.mapListIfSuccess(this.orderService.getDeleted(), OrderDto::fromEntity));
	}
	
	

}

