package com.gp.KuryeNet.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.CourierService;
import com.gp.KuryeNet.business.abstracts.check.CourierCheckService;
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
import com.gp.KuryeNet.entities.concretes.Courier;
import com.gp.KuryeNet.entities.concretes.Order;
import com.gp.KuryeNet.entities.dtos.CourierWithVehicleDto;
import com.gp.KuryeNet.entities.dtos.StartOrderWithCourierDto;


@Service
public class CourierManager implements CourierService{
	
	private CourierDao courierDao;
	private CourierCheckService courierCheckService;
	private OrderDao orderDao;
	private OrderCheckService orderCheckService;
	
	@Autowired
	public CourierManager(CourierDao courierDao, CourierCheckService courierCheckService,OrderDao orderDao,OrderCheckService orderCheckService) {
		super();
		this.courierDao = courierDao;
		this.courierCheckService = courierCheckService;
		this.orderDao = orderDao;
		this.orderCheckService = orderCheckService;
	}

	@Override
	public DataResult<List<Courier>> getAll() {
		return new SuccessDataResult<List<Courier>>(this.courierDao.findAll());
	}

	@Override
	public Result add(Courier courier) {
		courierCheckService.existsByCourierEmail(courier.getCourierEmail());
		courierCheckService.existsByCourierIdentityNumber(courier.getCourierIdentityNumber());
		courierCheckService.validEmail(courier.getCourierEmail());
		courierCheckService.validIdentityNumber(courier.getCourierIdentityNumber());
		ErrorDataResult<ApiError> errors= Utils.getErrorsIfExist(courierCheckService);
		if(errors!=null) return errors;
		else this.courierDao.save(courier);
		
		return new SuccessResult("courier added");
	}

	@Override
	public DataResult<Courier> getByCourierNameAndCourierSurname(String courierName, String courierSurname) {
		return new SuccessDataResult<Courier>(this.courierDao.getByCourierNameAndCourierSurname(courierName, courierSurname));
	}

	@Override
	public DataResult<Courier> getByCourierIdentityNumber(String courierIdentityNumber) {
		return new SuccessDataResult<Courier>(this.courierDao.getByCourierIdentityNumber(courierIdentityNumber));

	}

	@Override
	public DataResult<Courier> getByCourierEmail(String courierEmail) {
		return new SuccessDataResult<Courier>(this.courierDao.getByCourierEmail(courierEmail));

	}

	@Override
	public DataResult<List<Courier>> getAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1,pageSize);
		return new SuccessDataResult<List<Courier>>(this.courierDao.findAll(pageable).getContent());
	}

	@Override
	public DataResult<List<Courier>> getAllSortedByCourierName() {
		Sort sort = Sort.by(Sort.Direction.ASC,"courierName");
		return new SuccessDataResult<List<Courier>>(this.courierDao.findAll(sort),"ASC Couriers listed successfully");
	}

	@Override
	public DataResult<List<CourierWithVehicleDto>> getCourierWithVehicleDetails() {
		return new SuccessDataResult<List<CourierWithVehicleDto>>(this.courierDao.getCourierWithVehicleDetails());

	}

	@Override
	public DataResult<List<Courier>> getByCourierAddress_City(String city) {
		return new SuccessDataResult<List<Courier>>(this.courierDao.getByCourierAddress_City(city));

	}

	@Override
	public DataResult<Courier> getByCourierId(int courierId) {
		courierCheckService.existsCourierById(courierId);
		return new SuccessDataResult<Courier>(this.courierDao.getByCourierId(courierId));
	}

	@Override
	public DataResult<Integer> getByCourierIdWithOrderId(int orderId) {
		return new SuccessDataResult<Integer>(this.courierDao.getByCourierIdWithOrderId(orderId));
	}
	
	@Override
	public Result startOrder(int orderId, String courierEmail) {
		orderCheckService.existsOrderById(orderId);
		orderCheckService.availableOrder(orderId);
		courierCheckService.availableCourier(courierEmail);
		//courierCheckService.existsCourierById(courierEmail);
		ErrorDataResult<ApiError> errors= Utils.getErrorsIfExist(courierCheckService,orderCheckService);
		if(errors!=null) return errors;
		
		Order order = this.orderDao.getByOrderId(orderId);
		Courier courier = this.courierDao.getByCourierEmail(courierEmail);
		order.setCourier(courier);
		courier.setCourierStatus(200);
		order.setOrderStatus(200);
		orderDao.save(order);
		courierDao.save(courier);
		return new SuccessResult("Order start successfully");
	}
	
	@Override
	public Result endOrder(int orderId, String courierEmail) {
		orderCheckService.existsOrderById(orderId);
		orderCheckService.distributionOrder(orderId);
		courierCheckService.distributionCourier(courierEmail);
		//courierCheckService.existsCourierById(courierEmail);
		ErrorDataResult<ApiError> errors= Utils.getErrorsIfExist(courierCheckService,orderCheckService);
		if(errors!=null) return errors;
		
		Order order = this.orderDao.getByOrderId(orderId);
		Courier courier = this.courierDao.getByCourierEmail(courierEmail);
		courier.setCourierStatus(100);
		order.setOrderStatus(300);
		orderDao.save(order);
		courierDao.save(courier);
		return new SuccessResult("Order end successfully");
	}

	@Override
	public Result updateCourierCoordinates(String courierEmail, double latitude, double longitude) {
		
		Courier courier = this.courierDao.getByCourierEmail(courierEmail);
		courier.setCourierLatitude(latitude);
		courier.setCourierLongitude(longitude);
		courierDao.save(courier);
		return new SuccessResult("Courier coordinates are changed successfully");
	}


}
