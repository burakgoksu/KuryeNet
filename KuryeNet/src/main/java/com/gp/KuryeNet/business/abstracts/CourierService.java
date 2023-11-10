package com.gp.KuryeNet.business.abstracts;

import java.util.List;

import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.entities.concretes.Courier;
import com.gp.KuryeNet.entities.dtos.CourierWithVehicleDto;
import com.gp.KuryeNet.entities.dtos.StartOrderWithCourierDto;

public interface CourierService {
	
	DataResult<List<Courier>> getAll();
	
	DataResult<List<Courier>> getAll(int pageNo, int pageSize);
	
	DataResult<List<Courier>> getAllSortedByCourierName();
	
	Result add(Courier courier);
	
	Result updateCourierCoordinates(int courierId, double latitude,double longitude);
	
	Result startOrder(int orderId, int courierId);
	
	Result endOrder(int orderId, int courierId);
		
	DataResult<Integer> getByCourierIdWithOrderId(int orderId);
			
	DataResult<Courier> getByCourierId(int courierId);
	
	DataResult<Courier> getByCourierNameAndCourierSurname(String courierName, String courierSurname);
	
	DataResult<Courier> getByCourierIdentityNumber(String courierIdentityNumber);
	
	DataResult<Courier> getByCourierEmail(String courierEmail);
	
	DataResult<List<Courier>> getByCourierAddress_City(String city);
	
	DataResult<List<CourierWithVehicleDto>> getCourierWithVehicleDetails();
}
