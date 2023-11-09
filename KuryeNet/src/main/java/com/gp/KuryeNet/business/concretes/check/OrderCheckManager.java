package com.gp.KuryeNet.business.concretes.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.check.OrderCheckService;
import com.gp.KuryeNet.core.utulities.Util.CheckUtils;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.core.utulities.exception.exceptions.EntityNotExistsException;
import com.gp.KuryeNet.dataAccess.abstracts.OrderDao;

import lombok.SneakyThrows;

@Service
public class OrderCheckManager extends BaseCheckManager implements OrderCheckService{

	private OrderDao orderDao;
	
	@Autowired
	public OrderCheckManager(OrderDao orderDao) {
		super();
		this.orderDao = orderDao;
	}

	@Override
	@SneakyThrows
	public void existsOrderById(int orderId) {
		if(CheckUtils.notExistsById(orderDao, orderId))
			throw new EntityNotExistsException(Msg.NOT_EXIST.get("Order"));
		
	}

	@Override
	public void existsByOrderNumber(String orderNumber) {
		if(orderDao.existsByOrderNumber(orderNumber))
			errors.put("orderNumber", Msg.IS_IN_USE.get("OrderNumber"));
		
	}
	
	@Override
	public void availableOrder(int orderId) {
		if(orderDao.getByOrderId(orderId).getOrderStatus() != 100)
			errors.put("orderId", Msg.IS_IN_DISTRIBUTION.get());
		
	}
	
	@Override
	public void distributionOrder(int orderId) {
		if(orderDao.getByOrderId(orderId).getOrderStatus() != 200)
			errors.put("courierId", Msg.IS_NOT_IN_DISTRIBUTION.get());
		
	}

}
