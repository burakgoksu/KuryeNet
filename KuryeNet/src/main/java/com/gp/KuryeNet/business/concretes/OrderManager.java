package com.gp.KuryeNet.business.concretes;

import java.util.Date;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;


import com.gp.KuryeNet.business.abstracts.OrderService;
import com.gp.KuryeNet.business.abstracts.check.OrderCheckService;
import com.gp.KuryeNet.core.entities.ApiError;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorDataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;
import com.gp.KuryeNet.dataAccess.abstracts.CourierDao;
import com.gp.KuryeNet.dataAccess.abstracts.OrderDao;
import com.gp.KuryeNet.entities.concretes.Address;
import com.gp.KuryeNet.entities.concretes.Courier;
import com.gp.KuryeNet.entities.concretes.Order;
import com.gp.KuryeNet.entities.dtos.OrderWithAddressDto;
import com.gp.KuryeNet.entities.dtos.OrderWithCourierDto;

@Service
public class OrderManager implements OrderService{
	private OrderDao orderDao;
	private OrderCheckService orderCheckService;
	private CourierDao courierDao;

	@Autowired
	public OrderManager(OrderDao orderDao,OrderCheckService orderCheckService,CourierDao courierDao) {
		super();
		this.orderDao = orderDao;
		this.orderCheckService = orderCheckService;
		this.courierDao = courierDao;
	}


	@Override
	public DataResult<List<Order>> getAll() {
		return new SuccessDataResult<List<Order>>(this.orderDao.findAll(),"Order Data Listed");
	}


	@Override
	public Result add(Order order) {
		orderCheckService.existsByOrderNumber(order.getOrderNumber());
		ErrorDataResult<ApiError> errors = Utils.getErrorsIfExist(orderCheckService);
		if(errors != null) return errors;
		else this.orderDao.save(order);
		
		return new SuccessResult("Order added");
	}


	@Override
	public DataResult<List<Order>> getByOrderDate(Date orderDate) {
		return new SuccessDataResult<List<Order>>(this.orderDao.getByOrderDate(orderDate),"Order Data Listed");
	}


	@Override
	public DataResult<List<Order>> getByOrderType(String orderType) {
		return new SuccessDataResult<List<Order>>(this.orderDao.getByOrderType(orderType),"Order Data Listed");

	}


	@Override
	public DataResult<List<Order>> getByOrderStatus(int orderStatus) {
		return new SuccessDataResult<List<Order>>(this.orderDao.getByOrderStatus(orderStatus),"Order Data Listed");

	}


	@Override
	public DataResult<Order> getByOrderNumber(String orderNumber) {
		//Business Code
		return new SuccessDataResult<Order>(this.orderDao.getByOrderNumber(orderNumber),"Order Data Listed");
	}


	@Override
	public DataResult<List<Order>> getByOrderDateAndOrderStatus(Date orderDate, int OrderStatus) {
		return new SuccessDataResult<List<Order>>(this.orderDao.getByOrderDateAndOrderStatus(orderDate,OrderStatus),"Order Data Listed");
	}


	@Override
	public DataResult<List<Order>> getByCourier_CourierIdIn(List<Integer> couriers) {
		return new SuccessDataResult<List<Order>>(this.orderDao.getByCourier_CourierIdIn(couriers),"Order Data Listed");

	}


	@Override
	public DataResult<List<Order>> getByOrderNumberContains(String orderNumber) {
		return new SuccessDataResult<List<Order>>(this.orderDao.getByOrderNumberContains(orderNumber),"Order Data Listed");

	}


	@Override
	public DataResult<List<Order>> getByOrderHourAndOrderStatus(int orderHour, int orderStatus) {
		return new SuccessDataResult<List<Order>>(this.orderDao.getByOrderHourAndOrderStatus(orderHour,orderStatus),"Order Data Listed");

	}


	@Override
	public DataResult<List<Order>> getByOrderHour(int orderHour) {
		return new SuccessDataResult<List<Order>>(this.orderDao.getByOrderHour(orderHour),"Order Data Listed");

	}


	@Override
	public DataResult<List<Order>> getAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1,pageSize);
		return new SuccessDataResult<List<Order>>(this.orderDao.findAll(pageable).getContent());
	}


	@Override
	public DataResult<List<Order>> getAllSortedByOrderDate() {
		Sort sort = Sort.by(Sort.Direction.ASC,"orderDate");
		return new SuccessDataResult<List<Order>>(this.orderDao.findAll(sort),"ASC orders listed successfully");

	}


	@Override
	public DataResult<List<OrderWithCourierDto>> getOrderWithCourierDetails() {
		return new SuccessDataResult<List<OrderWithCourierDto>>(this.orderDao.getOrderWithCourierDetails(),"Order With Courier Details Data Listed");

	}
	
	
	@Override
	public DataResult<List<Order>> getByOrderAddress_AddressId(int addressId) {
		return new SuccessDataResult<List<Order>>(this.orderDao.getByOrderAddress_AddressId(addressId),"Order Data Listed which selected address");

	}


	@Override
	public DataResult<List<OrderWithAddressDto>> getOrderWithAddressDetails() {
		return new SuccessDataResult<List<OrderWithAddressDto>>(this.orderDao.getOrderWithAddressDetails(),"Order With Address Details Data Listed");
	}


	@Override
	public DataResult<Order> getByOrderId(int orderId) {
		orderCheckService.existsOrderById(orderId);
		return new SuccessDataResult<Order>(this.orderDao.getByOrderId(orderId));
	}

}

