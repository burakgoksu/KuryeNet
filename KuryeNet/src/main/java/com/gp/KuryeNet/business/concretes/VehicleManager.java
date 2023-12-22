package com.gp.KuryeNet.business.concretes;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.VehicleService;
import com.gp.KuryeNet.business.abstracts.check.VehicleCheckService;
import com.gp.KuryeNet.core.entities.ApiError;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorDataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;
import com.gp.KuryeNet.dataAccess.abstracts.VehicleDao;
import com.gp.KuryeNet.entities.concretes.Vehicle;


@Service
public class VehicleManager implements VehicleService{
	
	private VehicleDao vehicleDao;
	private VehicleCheckService vehicleCheckService;

	@Autowired
	public VehicleManager(VehicleDao vehicleDao,VehicleCheckService vehicleCheckService) {
		super();
		this.vehicleDao = vehicleDao;
		this.vehicleCheckService = vehicleCheckService;
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


	@Async
	@Transactional
	@Override
	public Result add(Vehicle vehicle) {
		vehicleCheckService.validVehicleEmission(vehicle.getVehicleEmission());
		vehicleCheckService.validVehiclePlate(vehicle.getVehiclePlate());
		vehicleCheckService.existsByVehiclePlate(vehicle.getVehiclePlate());
		ErrorDataResult<ApiError> errors = Utils.getErrorsIfExist(vehicleCheckService);
		if(errors!=null) return errors;
		else this.vehicleDao.save(vehicle);
		return new SuccessResult("vehicle added");
	}


	@Override
	public DataResult<Vehicle> getByVehicleId(int vehicleId) {
		vehicleCheckService.existsVehicleById(vehicleId);
		return new SuccessDataResult<Vehicle>(this.vehicleDao.getByVehicleId(vehicleId));
	}

}
