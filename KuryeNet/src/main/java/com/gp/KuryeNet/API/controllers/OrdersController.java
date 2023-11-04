package com.gp.KuryeNet.API.controllers;

import com.gp.KuryeNet.business.abstracts.OrderService;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.entities.concretes.Address;
import com.gp.KuryeNet.entities.concretes.Order;
import com.gp.KuryeNet.entities.dtos.OrderWithAddressDto;
import com.gp.KuryeNet.entities.dtos.OrderWithCourierDto;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/orders")
public class OrdersController {
	
	private OrderService orderService;
	
	@Autowired
	public OrdersController(OrderService orderService) {
		super();
		this.orderService = orderService;
	}

	@GetMapping("/getall")
	public ResponseEntity<?> getAll(){
		return Utils.getResponseEntity(this.orderService.getAll());
		
	}
	
	@GetMapping("/getallByPage")
	public ResponseEntity<?> getAll(int pageNo,int pageSize){
		return Utils.getResponseEntity(this.orderService.getAll(pageNo,pageSize));
		
	}
	
	@GetMapping("/getAllSortedByOrderDate")
	public ResponseEntity<?> getAllSortedByOrderDate(){
		return Utils.getResponseEntity(this.orderService.getAllSortedByOrderDate());
		
	}
	
	@PostMapping("/add")
	public ResponseEntity<?> add(@Valid @RequestBody Order order){
		return Utils.getResponseEntity(this.orderService.add(order));
		
	}
	
	@GetMapping("/getByOrderNumber")
	public ResponseEntity<?> getByOrderNumber(@RequestParam String orderNumber){
		return Utils.getResponseEntity(this.orderService.getByOrderNumber(orderNumber));
	}
	
	@GetMapping("/getByOrderHourAndOrderStatus")
	public ResponseEntity<?> getByOrderHourAndOrderStatus(@RequestParam("orderHour") int orderHour,@RequestParam("orderStatus") int orderStatus ){
		return Utils.getResponseEntity(this.orderService.getByOrderHourAndOrderStatus(orderHour, orderStatus));
	}
	
	@GetMapping("/getByOrderStatus")
	public ResponseEntity<?> getByOrderStatus(@RequestParam int orderStatus){
		return Utils.getResponseEntity(this.orderService.getByOrderStatus(orderStatus));
	}
	
	@GetMapping("/getByOrderType")
	public ResponseEntity<?> getByOrderType(@RequestParam String orderType){
		return Utils.getResponseEntity(this.orderService.getByOrderType(orderType));
	}
	
	@GetMapping("/getByOrderDate")
	public ResponseEntity<?> getByOrderDate(@RequestParam Date orderDate){
		return Utils.getResponseEntity(this.orderService.getByOrderDate(orderDate));
	}
	
	@GetMapping("/getByCourierIdIn")
	public ResponseEntity<?> getByCourierIdIn(@RequestParam List<Integer> couriersId){
		return Utils.getResponseEntity(this.orderService.getByCourier_CourierIdIn(couriersId));
	}
	
	@GetMapping("/getByOrderWithCourierDetails")
	public ResponseEntity<?> getByOrderWithCourierDetails(){
		return Utils.getResponseEntity(this.orderService.getOrderWithCourierDetails());
	}
	
	@GetMapping("/getByOrderAddressId")
	public ResponseEntity<?> getByOrderAddress_AddressId(@RequestParam int addressId){
		return Utils.getResponseEntity(this.orderService.getByOrderAddress_AddressId(addressId));
	}
	
	@GetMapping("/getByOrderWithAddressDetails")
	public ResponseEntity<?> getByOrderWithAddressDetails(){
		return Utils.getResponseEntity(this.orderService.getOrderWithAddressDetails());
	}
	
	@GetMapping("/getByOrderId")
	public ResponseEntity<?> getByOrderId(int orderId){
		return Utils.getResponseEntity(this.orderService.getByOrderId(orderId));
		
	}
	
	

}
