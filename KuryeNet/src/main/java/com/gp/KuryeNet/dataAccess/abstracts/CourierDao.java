package com.gp.KuryeNet.dataAccess.abstracts;


import java.util.List;

import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.time.Instant;

import com.gp.KuryeNet.entities.concretes.Courier;
import com.gp.KuryeNet.entities.dtos.CourierWithOrderDto;
import com.gp.KuryeNet.entities.dtos.CourierWithVehicleDto;


@Repository
@Component
@Transactional
public interface CourierDao extends JpaRepository<Courier,Integer>{
	
	Courier getByCourierNameAndCourierSurname(String courierName, String courierSurname);
	
	Courier getByCourierIdentityNumber(String identityNumber);
	
	Courier getByCourierEmail(String email);
	
	Courier getByCourierId(int courierId);
	
	@Query("SELECT o.courier.courierId FROM Order o WHERE orderId=:orderId")
	int getByCourierIdWithOrderId(int orderId);
	
	List<Courier> getByCourierAddress_City(String city);
	
	boolean existsByCourierIdentityNumber(String identityNumber);
	
	boolean existsByCourierEmail(String courierEmail);
	
	@Query("SELECT NEW com.gp.KuryeNet.entities.dtos.CourierWithVehicleDto(c.courierId,c.courierName,c.courierSurname,v.vehicleType,v.vehicleBrand,v.vehiclePlate) FROM Vehicle v INNER JOIN v.couriers c" )
	List<CourierWithVehicleDto> getCourierWithVehicleDetails();
	
	@Query("SELECT NEW com.gp.KuryeNet.entities.dtos.CourierWithOrderDto(c.courierId,c.courierLatitude,c.courierLongitude,c.courierStatus,o.orderStatus,o.remainingMinutes, o.remainingDistance, o.deliveryDate, o.estimatedDeliveryTime) FROM Order o INNER JOIN o.courier c where o.orderNumber=:orderNumber" )
	CourierWithOrderDto getCourierWithOrderDetails(String orderNumber);

	@Query(value = "SELECT * FROM couriers WHERE deleted = true", nativeQuery = true)
	List<Courier> getDeleted();

	@Modifying
	@Query("UPDATE Courier c SET c.deleted = false, c.deletedAt = null, c.deletedBy = null WHERE c.courierId = :id")
	int restoreById(@Param("id") int id);

	@Modifying
	@Query("UPDATE Courier c SET c.deleted = true, c.deletedAt = :deletedAt, c.deletedBy = :deletedBy WHERE c.courierId = :id")
	int softDeleteById(@Param("id") int id, @Param("deletedAt") Instant deletedAt, @Param("deletedBy") String deletedBy);
	
	
	
}
