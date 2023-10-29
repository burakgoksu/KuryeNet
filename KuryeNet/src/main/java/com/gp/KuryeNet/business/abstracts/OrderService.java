package com.gp.KuryeNet.business.abstracts;

import java.util.Date;
import java.util.List;

import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.entities.concretes.Order;
import com.gp.KuryeNet.entities.dtos.OrderWithCourierDto;

public interface OrderService {
	
	DataResult<List<Order>> getAll();
	
	DataResult<List<Order>> getAll(int pageNo, int pageSize);
	
	DataResult<List<Order>> getAllSortedByOrderDate();

	Result add(Order order);
	
	DataResult<List<Order>> getByOrderDate(Date orderDate);
	
	DataResult<List<Order>> getByOrderType(String orderType);
	
	DataResult<List<Order>> getByOrderStatus(int orderStatus);
	
	DataResult<Order> getByOrderNumber(String orderNumber);
	
	DataResult<List<Order>> getByOrderDateAndOrderStatus(Date orderDate, int OrderStatus);
	
	DataResult<List<Order>> getByCourier_CourierIdIn(List<Integer> couriers);
	
	DataResult<List<Order>> getByOrderNumberContains(String orderNumber);
	
	DataResult<List<Order>> getByOrderHourAndOrderStatus(int orderHour, int orderStatus);
	
	DataResult<List<Order>> getByOrderHour(int orderHour);
	
	DataResult<List<OrderWithCourierDto>> getOrderWithCourierDetails();
	
}
