package com.gp.KuryeNet.dataAccess.abstracts;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.gp.KuryeNet.entities.concretes.Courier;
import com.gp.KuryeNet.entities.dtos.CourierWithVehicleDto;


@Repository
@Component
public interface CourierDao extends JpaRepository<Courier,Integer>{
	
	Courier getByCourierNameAndCourierSurname(String courierName, String courierSurname);
	
	Courier getByCourierIdentityNumber(String identityNumber);
	
	Courier getByCourierEmail(String email);
	
	List<Courier> getByCourierAddress_City(String city);
	
	@Query("SELECT NEW com.gp.KuryeNet.entities.dtos.CourierWithVehicleDto(c.courierId,c.courierName,c.courierSurname,v.vehicleType,v.vehicleBrand,v.vehiclePlate) FROM Vehicle v INNER JOIN v.couriers c" )
	List<CourierWithVehicleDto> getCourierWithVehicleDetails();
	
}