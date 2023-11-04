package com.gp.KuryeNet.dataAccess.abstracts;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.gp.KuryeNet.entities.concretes.Vehicle;

@Repository
@Component
@Transactional
public interface VehicleDao extends JpaRepository<Vehicle, Integer>{
	
	Vehicle getByVehiclePlate(String vehiclePlate);
	
	List<Vehicle> getByVehicleType(String vehicleType);
	
	List<Vehicle> getByVehicleBrand(String vehicleBrand);
	
	List<Vehicle> getByVehicleModel(String vehicleModel);
	
	List<Vehicle> getByVehicleYear(String vehicleYear);
	
	List<Vehicle> getByVehicleYear(int vehicleYear);
		
	List<Vehicle> getByCouriers_CourierId(int courierId);
	
	List<Vehicle> getByCouriers_CourierIdentityNumber(String courierIdentityNumber);
	
	List<Vehicle> getByCouriers_CourierNameAndCouriers_CourierSurname(String courierName, String courierSurname);

}
