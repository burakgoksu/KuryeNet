package com.gp.KuryeNet.business.concretes;

import java.util.Date;
import java.util.List;
import java.time.Instant;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.beans.factory.ObjectProvider;

import com.gp.KuryeNet.business.abstracts.OrderService;
import com.gp.KuryeNet.business.abstracts.check.OrderCheckService;
import com.gp.KuryeNet.core.entities.ApiError;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorDataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;
import com.gp.KuryeNet.core.utulities.security.SecurityUtils;
import com.gp.KuryeNet.dataAccess.abstracts.CourierDao;
import com.gp.KuryeNet.dataAccess.abstracts.OrderDao;
import com.gp.KuryeNet.core.messaging.OrderEventPublisher;
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
	private ObjectProvider<OrderEventPublisher> orderEventPublisher;

	@Autowired
	public OrderManager(OrderDao orderDao,OrderCheckService orderCheckService,CourierDao courierDao,
			ObjectProvider<OrderEventPublisher> orderEventPublisher) {
		super();
		this.orderDao = orderDao;
		this.orderCheckService = orderCheckService;
		this.courierDao = courierDao;
		this.orderEventPublisher = orderEventPublisher;
	}


	@Async
	@Override
	public DataResult<List<Order>> getAll() {
		return new SuccessDataResult<List<Order>>(this.orderDao.findAll(),"Order Data Listed");
	}


	@Async
	@Transactional
	@Override
	@CacheEvict(cacheNames = {"ordersById","ordersByNumber","ordersByStatus","ordersByType","ordersByDate","ordersByDateAndStatus","ordersByHour","ordersByHourAndStatus","ordersByAddressId","ordersWithCourier","ordersWithAddress"}, allEntries = true)
	public Result add(Order order) {
		orderCheckService.existsByOrderNumber(order.getOrderNumber());
		ErrorDataResult<ApiError> errors = Utils.getErrorsIfExist(orderCheckService);
		if(errors != null) return errors;
		else this.orderDao.save(order);

		orderEventPublisher.ifAvailable(publisher -> publisher.publishOrderCreated(order));
		
		return new SuccessResult("Order added");
	}


	@Override
	@Cacheable(cacheNames = "ordersByDate", key = "#orderDate")
	public DataResult<List<Order>> getByOrderDate(Date orderDate) {
		return new SuccessDataResult<List<Order>>(this.orderDao.getByOrderDate(orderDate),"Order Data Listed");
	}


	@Override
	@Cacheable(cacheNames = "ordersByType", key = "#orderType")
	public DataResult<List<Order>> getByOrderType(String orderType) {
		return new SuccessDataResult<List<Order>>(this.orderDao.getByOrderType(orderType),"Order Data Listed");

	}


	@Override
	@Cacheable(cacheNames = "ordersByStatus", key = "#orderStatus")
	public DataResult<List<Order>> getByOrderStatus(int orderStatus) {
		return new SuccessDataResult<List<Order>>(this.orderDao.getByOrderStatus(orderStatus),"Order Data Listed");

	}


	@Override
	@Cacheable(cacheNames = "ordersByNumber", key = "#orderNumber")
	public DataResult<Order> getByOrderNumber(String orderNumber) {
		//Business Code
		return new SuccessDataResult<Order>(this.orderDao.getByOrderNumber(orderNumber),"Order Data Listed");
	}


	@Override
	@Cacheable(cacheNames = "ordersByDateAndStatus", key = "#orderDate.toString() + ':' + #OrderStatus")
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
	@Cacheable(cacheNames = "ordersByHourAndStatus", key = "#orderHour + ':' + #orderStatus")
	public DataResult<List<Order>> getByOrderHourAndOrderStatus(int orderHour, int orderStatus) {
		return new SuccessDataResult<List<Order>>(this.orderDao.getByOrderHourAndOrderStatus(orderHour,orderStatus),"Order Data Listed");

	}


	@Override
	@Cacheable(cacheNames = "ordersByHour", key = "#orderHour")
	public DataResult<List<Order>> getByOrderHour(int orderHour) {
		return new SuccessDataResult<List<Order>>(this.orderDao.getByOrderHour(orderHour),"Order Data Listed");

	}

	
	@Async
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
	@Cacheable(cacheNames = "ordersWithCourier")
	public DataResult<List<OrderWithCourierDto>> getOrderWithCourierDetails() {
		return new SuccessDataResult<List<OrderWithCourierDto>>(this.orderDao.getOrderWithCourierDetails(),"Order With Courier Details Data Listed");

	}
	
	
	@Override
	@Cacheable(cacheNames = "ordersByAddressId", key = "#addressId")
	public DataResult<List<Order>> getByOrderAddress_AddressId(int addressId) {
		return new SuccessDataResult<List<Order>>(this.orderDao.getByOrderAddress_AddressId(addressId),"Order Data Listed which selected address");

	}


	@Override
	@Cacheable(cacheNames = "ordersWithAddress")
	public DataResult<List<OrderWithAddressDto>> getOrderWithAddressDetails() {
		return new SuccessDataResult<List<OrderWithAddressDto>>(this.orderDao.getOrderWithAddressDetails(),"Order With Address Details Data Listed");
	}


	@Override
	@Cacheable(cacheNames = "ordersById", key = "#orderId")
	public DataResult<Order> getByOrderId(int orderId) {
		orderCheckService.existsOrderById(orderId);
		return new SuccessDataResult<Order>(this.orderDao.getByOrderId(orderId));
	}

	@Override
	@CacheEvict(cacheNames = {"ordersById","ordersByNumber","ordersByStatus","ordersByType","ordersByDate","ordersByDateAndStatus","ordersByHour","ordersByHourAndStatus","ordersByAddressId","ordersWithCourier","ordersWithAddress"}, allEntries = true)
	public Result delete(int orderId) {
		orderCheckService.existsOrderById(orderId);
		ErrorDataResult<ApiError> errors = Utils.getErrorsIfExist(orderCheckService);
		if(errors != null) return errors;

		int updated = orderDao.softDeleteById(orderId, Instant.now(), SecurityUtils.resolveCurrentUser());
		if (updated == 0) {
			return new ErrorResult(Msg.NOT_FOUND.get());
		}
		return new SuccessResult("order deleted");
	}

	@Override
	@CacheEvict(cacheNames = {"ordersById","ordersByNumber","ordersByStatus","ordersByType","ordersByDate","ordersByDateAndStatus","ordersByHour","ordersByHourAndStatus","ordersByAddressId","ordersWithCourier","ordersWithAddress"}, allEntries = true)
	public Result restore(int orderId) {
		int updated = orderDao.restoreById(orderId);
		if (updated == 0) {
			return new ErrorResult(Msg.NOT_FOUND.get());
		}
		return new SuccessResult("order restored");
	}

	@Override
	public DataResult<List<Order>> getDeleted() {
		return new SuccessDataResult<List<Order>>(orderDao.getDeleted());
	}

}

