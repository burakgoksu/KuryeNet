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

import com.gp.KuryeNet.entities.concretes.Provider;
import com.gp.KuryeNet.entities.concretes.Vehicle;

@Repository
@Component
@Transactional
public interface VehicleDao extends JpaRepository<Vehicle, Integer>{
	
	Vehicle getByVehiclePlate(String vehiclePlate);
	
	Vehicle getByVehicleId(int vehicleId);
		
	boolean existsByVehiclePlate(String vehiclePlate);
	
	List<Vehicle> getByVehicleType(String vehicleType);
	
	List<Vehicle> getByVehicleBrand(String vehicleBrand);
	
	List<Vehicle> getByVehicleModel(String vehicleModel);
	
	List<Vehicle> getByVehicleYear(int vehicleYear);
		
	List<Vehicle> getByCouriers_CourierId(int courierId);
	
	List<Vehicle> getByCouriers_CourierIdentityNumber(String courierIdentityNumber);
	
	List<Vehicle> getByCouriers_CourierNameAndCouriers_CourierSurname(String courierName, String courierSurname);

	@Query(value = "SELECT * FROM vehicles WHERE deleted = true", nativeQuery = true)
	List<Vehicle> getDeleted();

	@Modifying
	@Query("UPDATE Vehicle v SET v.deleted = false, v.deletedAt = null, v.deletedBy = null WHERE v.vehicleId = :id")
	int restoreById(@Param("id") int id);

	@Modifying
	@Query("UPDATE Vehicle v SET v.deleted = true, v.deletedAt = :deletedAt, v.deletedBy = :deletedBy WHERE v.vehicleId = :id")
	int softDeleteById(@Param("id") int id, @Param("deletedAt") Instant deletedAt, @Param("deletedBy") String deletedBy);
}
