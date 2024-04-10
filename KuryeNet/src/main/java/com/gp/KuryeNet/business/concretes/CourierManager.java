package com.gp.KuryeNet.business.concretes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.CourierService;
import com.gp.KuryeNet.business.abstracts.check.AddressCheckService;
import com.gp.KuryeNet.business.abstracts.check.CourierCheckService;
import com.gp.KuryeNet.business.abstracts.check.OrderCheckService;
import com.gp.KuryeNet.business.abstracts.check.VehicleCheckService;
import com.gp.KuryeNet.core.business.abstracts.AIModelService;
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
import com.gp.KuryeNet.entities.dtos.CourierWithOrderDto;
import com.gp.KuryeNet.entities.dtos.CourierWithVehicleDto;
import com.gp.KuryeNet.entities.dtos.StartOrderWithCourierDto;

import net.bytebuddy.asm.Advice.Local;


@Service
public class CourierManager implements CourierService{
	
	private CourierDao courierDao;
	private CourierCheckService courierCheckService;
	private OrderDao orderDao;
	private OrderCheckService orderCheckService;
	private AddressCheckService addressCheckService;
	private VehicleCheckService vehicleCheckService;
	private AIModelService aiModelService;
	
	@Autowired
	public CourierManager(CourierDao courierDao, CourierCheckService courierCheckService,OrderDao orderDao,OrderCheckService orderCheckService,AddressCheckService addressCheckService,VehicleCheckService vehicleCheckService) {
		super();
		this.courierDao = courierDao;
		this.courierCheckService = courierCheckService;
		this.orderDao = orderDao;
		this.orderCheckService = orderCheckService;
		this.addressCheckService = addressCheckService;
		this.vehicleCheckService = vehicleCheckService;
	}

	@Override
	public DataResult<List<Courier>> getAll() {
		return new SuccessDataResult<List<Courier>>(this.courierDao.findAll());
	}

	@Transactional
	@Async
	@Override
	public Result add(Courier courier) {
		addressCheckService.existsPhoneNumber(courier.getCourierAddress().getPhoneNumber());
		addressCheckService.validPhoneNumber(courier.getCourierAddress().getPhoneNumber());
		vehicleCheckService.validVehicleEmission(courier.getVehicle().getVehicleEmission());
		vehicleCheckService.validVehiclePlate(courier.getVehicle().getVehiclePlate());
		vehicleCheckService.existsByVehiclePlate(courier.getVehicle().getVehiclePlate());
		//courierCheckService.existsByCourierEmail(courier.getCourierEmail());
		courierCheckService.existsByCourierIdentityNumber(courier.getCourierIdentityNumber());
		courierCheckService.validEmail(courier.getCourierEmail());
		courierCheckService.validIdentityNumber(courier.getCourierIdentityNumber());
		//courierCheckService.existsInUserByEmail(courier.getCourierEmail());
		ErrorDataResult<ApiError> errors= Utils.getErrorsIfExist(courierCheckService,addressCheckService);
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

	@Async
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
	
	@Async
	@Transactional
	@Override
	public Result startOrder(int orderId, String courierEmail) {
		orderCheckService.existsOrderById(orderId);
		orderCheckService.availableOrder(orderId);
		courierCheckService.availableCourier(courierEmail);
		courierCheckService.existsCourierById(courierDao.getByCourierEmail(courierEmail).getCourierId());
		ErrorDataResult<ApiError> errors= Utils.getErrorsIfExist(courierCheckService,orderCheckService);
		if(errors!=null) return errors;
		
		Order order = this.orderDao.getByOrderId(orderId);
		Courier courier = this.courierDao.getByCourierEmail(courierEmail);
		order.setCourier(courier);
		courier.setCourierStatus(200);
		order.setOrderStatus(200);
		orderDao.save(order);
		courierDao.save(courier);
		aiModelService.getPrediction(courierEmail, orderId);
		return new SuccessResult("Order start successfully");
	}
	
	@Async
	@Transactional
	@Override
	public Result endOrder(int orderId, String courierEmail) {
		orderCheckService.existsOrderById(orderId);
		orderCheckService.distributionOrder(orderId);
		courierCheckService.distributionCourier(courierEmail);
		courierCheckService.existsCourierById(courierDao.getByCourierEmail(courierEmail).getCourierId());
		ErrorDataResult<ApiError> errors= Utils.getErrorsIfExist(courierCheckService,orderCheckService);
		if(errors!=null) return errors;
		
		Instant instant = Instant.now();
        Date deliveryDate = Date.from(instant);
        
		Order order = this.orderDao.getByOrderId(orderId);
		Courier courier = this.courierDao.getByCourierEmail(courierEmail);
		
		LocalDate today = LocalDate.now();
	    LocalDate lastUpdated = courier.getLastDailyUpdate();
	    
	    if (lastUpdated == null || !lastUpdated.isEqual(today)) {
	        courier.setTotal_shipped(0);
	        courier.setLastDailyUpdate(today);
	    }
	    
	    int tempDailyShipped = courier.getTotal_shipped();
		courier.setDaily_shipped(tempDailyShipped+1);
	    
		int tempTotalShipped = courier.getTotal_shipped();
		courier.setTotal_shipped(tempTotalShipped+1);
		courier.setCourierStatus(100);
		order.setOrderStatus(300);
		order.setDeliveryDate(deliveryDate);	
		
		
		String order_date = order.getOrderDate().toString();
		String delivery_date = order.getDeliveryDate().toString();
		
		SimpleDateFormat incomingFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss 'TRT' yyyy",Locale.ENGLISH);
		incomingFormat.setTimeZone(TimeZone.getTimeZone("GMT+3"));
	    SimpleDateFormat targetFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	    
	    String targetDeliveryDate = null;
	    try {
			Date incomingDate = incomingFormat.parse(delivery_date);
		    targetDeliveryDate = targetFormat.format(incomingDate);
		    System.out.println("targetDateStr: "+targetDeliveryDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	    
					
		if (order_date.contains(":")) {
			order_date = order_date.substring(0, order_date.lastIndexOf(":"));
	    }
		if (targetDeliveryDate.contains(":")) {
			targetDeliveryDate = targetDeliveryDate.substring(0, targetDeliveryDate.lastIndexOf(":"));
	    }     
	    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
	    LocalDateTime order_date_format = LocalDateTime.parse(order_date, formatter);
	    LocalDateTime delivery_date_format = LocalDateTime.parse(targetDeliveryDate, formatter);
	    long minutesBetween = ChronoUnit.MINUTES.between(order_date_format,delivery_date_format);
	    int timeTaken = (int) minutesBetween;
	    
	    order.setTimeTaken(timeTaken);
		
		orderDao.save(order);
		courierDao.save(courier);
		aiModelService.writeData(courierEmail, orderId);
		return new SuccessResult("Order end successfully");
	}

	@Async
	@Transactional
	@Override
	public Result updateCourierCoordinates(String courierEmail, double latitude, double longitude) {
		
		Courier courier = this.courierDao.getByCourierEmail(courierEmail);
		courier.setCourierLatitude(latitude);
		courier.setCourierLongitude(longitude);
		courierDao.save(courier);
		return new SuccessResult("Courier coordinates are changed successfully");
	}

	@Override
	public DataResult<Boolean> existsByCourierEmail(String courierEmail) {
		//courierCheckService.existsByCourierEmail(courierEmail);
		return new SuccessDataResult<Boolean>(this.courierDao.existsByCourierEmail(courierEmail));

	}

	@Async
	@Override
	public DataResult<CourierWithOrderDto> getCourierWithOrderDetails(String orderNumber) {
		return new SuccessDataResult<CourierWithOrderDto>(this.courierDao.getCourierWithOrderDetails(orderNumber),"CourierWithOrderDetails listed!");

	}


}
