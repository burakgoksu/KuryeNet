package com.gp.KuryeNet.business.abstracts;

import java.util.List;

import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.entities.concretes.Vehicle;

public interface VehicleService {
	
	DataResult<List<Vehicle>> getAll();
	
	DataResult<List<Vehicle>> getAll(int pageNo, int pageSize);
	
	DataResult<List<Vehicle>> getAllSortedByYear();
	
	DataResult<List<Vehicle>> getAllSortedByBrand();

	Result add(Vehicle vehicle);
	
	DataResult<Vehicle> getByVehicleId(int vehicleId);
	
	DataResult<Vehicle> getByVehiclePlate(String vehiclePlate);
	
	DataResult<List<Vehicle>> getByVehicleType(String vehicleType);
	
	DataResult<List<Vehicle>> getByVehicleBrand(String vehicleBrand);
	
	DataResult<List<Vehicle>> getByVehicleModel(String vehicleModel);
	
	DataResult<List<Vehicle>> getByVehicleYear(int vehicleYear);
			
	DataResult<List<Vehicle>> getByCouriers_CourierId(int courierId);
	
	DataResult<List<Vehicle>> getByCouriers_CourierIdentityNumber(String courierIdentityNumber);
	
	DataResult<List<Vehicle>> getByCouriers_CourierNameAndCouriers_CourierSurname(String courierName, String courierSurname);


}
