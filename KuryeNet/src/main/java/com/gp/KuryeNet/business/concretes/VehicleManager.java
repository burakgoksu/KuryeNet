package com.gp.KuryeNet.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.VehicleService;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;
import com.gp.KuryeNet.dataAccess.abstracts.VehicleDao;
import com.gp.KuryeNet.entities.concretes.Vehicle;

@Service
public class VehicleManager implements VehicleService{
	
	private VehicleDao vehicleDao;

	@Autowired
	public VehicleManager(VehicleDao vehicleDao) {
		super();
		this.vehicleDao = vehicleDao;
	}

	
	@Override
	public DataResult<List<Vehicle>> getAll() {
		return new SuccessDataResult<List<Vehicle>>(this.vehicleDao.findAll(),"Vehicle Data Listed");
	}

	@Override
	public DataResult<List<Vehicle>> getAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1,pageSize);
		return new SuccessDataResult<List<Vehicle>>(this.vehicleDao.findAll(pageable).getContent());
	}

	@Override
	public DataResult<List<Vehicle>> getAllSortedByYear() {
		Sort sort = Sort.by(Sort.Direction.DESC,"vehicleYear");
		return new SuccessDataResult<List<Vehicle>>(this.vehicleDao.findAll(sort),"Year, Vehicle sorted successfully");
	}

	@Override
	public DataResult<List<Vehicle>> getAllSortedByBrand() {
		Sort sort = Sort.by(Sort.Direction.ASC,"vehicleBrand");
		return new SuccessDataResult<List<Vehicle>>(this.vehicleDao.findAll(sort),"Brand, Vehicle sorted successfully");
	}

	@Override
	public DataResult<Vehicle> getByVehiclePlate(String vehiclePlate) {
		return new SuccessDataResult<Vehicle>(this.vehicleDao.getByVehiclePlate(vehiclePlate),"Vehicle Data Listed");
	}

	@Override
	public DataResult<List<Vehicle>> getByVehicleType(String vehicleType) {
		return new SuccessDataResult<List<Vehicle>>(this.vehicleDao.getByVehicleType(vehicleType),"Vehicle Data Listed");
	}

	@Override
	public DataResult<List<Vehicle>> getByVehicleBrand(String vehicleBrand) {
		return new SuccessDataResult<List<Vehicle>>(this.vehicleDao.getByVehicleBrand(vehicleBrand),"Vehicle Data Listed");
	}

	@Override
	public DataResult<List<Vehicle>> getByVehicleModel(String vehicleModel) {
		return new SuccessDataResult<List<Vehicle>>(this.vehicleDao.getByVehicleModel(vehicleModel),"Vehicle Data Listed");
	}


	@Override
	public DataResult<List<Vehicle>> getByVehicleYear(int vehicleYear) {
		return new SuccessDataResult<List<Vehicle>>(this.vehicleDao.getByVehicleYear(vehicleYear),"Vehicle Data Listed");
	}

	@Override
	public DataResult<List<Vehicle>> getByCouriers_CourierId(int courierId) {
		return new SuccessDataResult<List<Vehicle>>(this.vehicleDao.getByCouriers_CourierId(courierId),"Vehicle Data Listed");
	}

	@Override
	public DataResult<List<Vehicle>> getByCouriers_CourierIdentityNumber(String courierIdentityNumber) {
		return new SuccessDataResult<List<Vehicle>>(this.vehicleDao.getByCouriers_CourierIdentityNumber(courierIdentityNumber),"Vehicle Data Listed");
	}

	@Override
	public DataResult<List<Vehicle>> getByCouriers_CourierNameAndCouriers_CourierSurname(String courierName, String courierSurname) {
		return new SuccessDataResult<List<Vehicle>>(this.vehicleDao.getByCouriers_CourierNameAndCouriers_CourierSurname(courierName,courierSurname),"Vehicle Data Listed");
	}


	@Override
	public Result add(Vehicle vehicle) {
		this.vehicleDao.save(vehicle);
		return new SuccessResult("vehicle added");
	}

}
