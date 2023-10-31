package com.gp.KuryeNet.API.controllers;

import com.gp.KuryeNet.business.abstracts.OrderService;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.entities.concretes.Address;
import com.gp.KuryeNet.entities.concretes.Order;
import com.gp.KuryeNet.entities.dtos.OrderWithAddressDto;
import com.gp.KuryeNet.entities.dtos.OrderWithCourierDto;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
	public DataResult<List<Order>> getAll(){
		return this.orderService.getAll();
		
	}
	
	@GetMapping("/getallByPage")
	public DataResult<List<Order>> getAll(int pageNo,int pageSize){
		return this.orderService.getAll(pageNo,pageSize);
		
	}
	
	@GetMapping("/getAllSortedByOrderDate")
	public DataResult<List<Order>> getAllSortedByOrderDate(){
		return this.orderService.getAllSortedByOrderDate();
		
	}
	
	@PostMapping("/add")
	public Result add(@RequestBody Order order){
		return this.orderService.add(order);
		
	}
	
	@GetMapping("/getByOrderNumber")
	public DataResult<Order> getByOrderNumber(@RequestParam String orderNumber){
		return this.orderService.getByOrderNumber(orderNumber);
	}
	
	@GetMapping("/getByOrderHourAndOrderStatus")
	public DataResult<List<Order>> getByOrderHourAndOrderStatus(@RequestParam("orderHour") int orderHour,@RequestParam("orderStatus") int orderStatus ){
		return this.orderService.getByOrderHourAndOrderStatus(orderHour, orderStatus);
	}
	
	@GetMapping("/getByOrderStatus")
	public DataResult<List<Order>> getByOrderStatus(@RequestParam int orderStatus){
		return this.orderService.getByOrderStatus(orderStatus);
	}
	
	@GetMapping("/getByOrderType")
	public DataResult<List<Order>> getByOrderType(@RequestParam String orderType){
		return this.orderService.getByOrderType(orderType);
	}
	
	@GetMapping("/getByOrderDate")
	public DataResult<List<Order>> getByOrderDate(@RequestParam Date orderDate){
		return this.orderService.getByOrderDate(orderDate);
	}
	
	@GetMapping("/getByCourierIdIn")
	public DataResult<List<Order>> getByCourierIdIn(@RequestParam List<Integer> couriersId){
		return this.orderService.getByCourier_CourierIdIn(couriersId);
	}
	
	@GetMapping("/getByOrderWithCourierDetails")
	public DataResult<List<OrderWithCourierDto>> getByOrderWithCourierDetails(){
		return this.orderService.getOrderWithCourierDetails();
	}
	
	@GetMapping("/getByOrderAddressId")
	public DataResult<List<Order>> getByOrderAddress_AddressId(@RequestParam int addressId){
		return this.orderService.getByOrderAddress_AddressId(addressId);
	}
	
	@GetMapping("/getByOrderWithAddressDetails")
	public DataResult<List<OrderWithAddressDto>> getByOrderWithAddressDetails(){
		return this.orderService.getOrderWithAddressDetails();
	}
	
	

}
