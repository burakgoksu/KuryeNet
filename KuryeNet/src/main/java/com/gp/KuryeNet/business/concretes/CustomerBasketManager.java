package com.gp.KuryeNet.business.concretes;

import java.util.List;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.CustomerBasketService;
import com.gp.KuryeNet.business.abstracts.check.CustomerBasketCheckService;
import com.gp.KuryeNet.business.abstracts.check.CustomerCheckService;
import com.gp.KuryeNet.business.abstracts.check.OrderCheckService;
import com.gp.KuryeNet.core.entities.ApiError;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorDataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;
import com.gp.KuryeNet.dataAccess.abstracts.CustomerBasketDao;
import com.gp.KuryeNet.dataAccess.abstracts.CustomerDao;
import com.gp.KuryeNet.dataAccess.abstracts.OrderDao;
import com.gp.KuryeNet.entities.concretes.Customer;
import com.gp.KuryeNet.entities.concretes.CustomerBasket;
import com.gp.KuryeNet.entities.concretes.Order;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.core.utulities.security.SecurityUtils;

@Service
public class CustomerBasketManager implements CustomerBasketService{
	
	private CustomerBasketDao customerBasketDao;
	private OrderCheckService orderCheckService;
	private CustomerBasketCheckService customerBasketCheckService;
	private CustomerDao customerDao;
	private OrderDao orderDao;

	@Autowired
	public CustomerBasketManager(CustomerBasketDao customerBasketDao,CustomerBasketCheckService customerBasketCheckService,CustomerDao customerDao,OrderDao orderDao,OrderCheckService orderCheckService) {
		super();
		this.customerBasketDao = customerBasketDao;
		this.orderCheckService = orderCheckService;
		this.customerBasketCheckService = customerBasketCheckService;
		this.customerDao = customerDao;
		this.orderDao = orderDao;
	}

	@Override
	public DataResult<List<CustomerBasket>> getAll() {
		return new SuccessDataResult<List<CustomerBasket>>(this.customerBasketDao.findAll());
	}

	@Override
	public DataResult<List<CustomerBasket>> getAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1,pageSize);
		return new SuccessDataResult<List<CustomerBasket>>(this.customerBasketDao.findAll(pageable).getContent());
	}

	@Override
	public Result add(String customerEmail, String orderNumber) {
		int orderId=0;
		customerBasketCheckService.existsOrderByNumber(orderNumber);
		Order order = orderDao.getByOrderNumber(orderNumber);
		if(order == null) {
			orderId = 0;
		}
		else {
			orderId=order.getOrderId();
		}
		orderCheckService.existsOrderById(orderId);
		ErrorDataResult<ApiError> errors= Utils.getErrorsIfExist(customerBasketCheckService, orderCheckService);
		if(errors!=null) return errors;
		
		Customer customer = customerDao.getByCustomerEmail(customerEmail);
		CustomerBasket customerBasket = new CustomerBasket();
		customerBasket.setCustomer(customer);
		customerBasket.setOrder(order);
		customerBasketDao.save(customerBasket);
	
		return new SuccessResult("customerBasket added");
	}

	@Override
	public DataResult<CustomerBasket> getByOrder_OrderNumber(String orderNumber) {
		return new SuccessDataResult<CustomerBasket>(this.customerBasketDao.getByOrder_OrderNumber(orderNumber));

	}

	@Override
	public DataResult<List<CustomerBasket>> getByCustomer_CustomerEmail(String customerEmail) {
		return new SuccessDataResult<List<CustomerBasket>>(this.customerBasketDao.getByCustomer_CustomerEmail(customerEmail));
	}

	@Override
	public Result delete(String orderNumber) {
		customerBasketCheckService.existsOrderByNumber2(orderNumber);
		ErrorDataResult<ApiError> errors = Utils.getErrorsIfExist(customerBasketCheckService);
		if(errors!=null) return errors;
		
		String deletedBy = SecurityUtils.resolveCurrentUser();
		int updated = customerBasketDao.softDeleteByOrderNumber(orderNumber, Instant.now(), deletedBy);
		if (updated == 0) {
			return new ErrorResult(Msg.NOT_FOUND.get());
		}
		return new SuccessResult("customerBasket deleted");
	}

	@Override
	public DataResult<List<CustomerBasket>> getDeleted() {
		return new SuccessDataResult<List<CustomerBasket>>(this.customerBasketDao.getDeleted());
	}

	@Override
	public Result restore(int customerBasketId) {
		int updated = customerBasketDao.restoreById(customerBasketId);
		if (updated == 0) {
			return new ErrorResult(Msg.NOT_EXIST.get("CustomerBasket"));
		}
		return new SuccessResult("customerBasket restored");
	}

}
