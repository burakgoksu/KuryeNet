package com.gp.KuryeNet.business.concretes.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.check.VehicleCheckService;
import com.gp.KuryeNet.core.utulities.Util.CheckUtils;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.core.utulities.exception.exceptions.EntityNotExistsException;
import com.gp.KuryeNet.dataAccess.abstracts.VehicleDao;

import lombok.SneakyThrows;

@Service
public class VehicleCheckManager extends BaseCheckManager implements VehicleCheckService{
	private VehicleDao vehicleDao;

	@Autowired
	public VehicleCheckManager(VehicleDao vehicleDao) {
		super();
		this.vehicleDao = vehicleDao;
	}

	@Override
	@SneakyThrows
	public void existsVehicleById(int vehicleId) {
		if(CheckUtils.notExistsById(vehicleDao, vehicleId))
			throw new EntityNotExistsException(Msg.NOT_EXIST.get("Vehicle"));
		
	}

	@Override
	public void existsByVehiclePlate(String vehiclePlate) {
		if(vehicleDao.existsByVehiclePlate(vehiclePlate))
			errors.put("vehiclePlate",Msg.IS_IN_USE.get("vehiclePlate"));
		
	}

	@Override
	public void validVehicleEmission(String vehilceEmission) {
		if(CheckUtils.invalidVehicleEmission(vehilceEmission))
			errors.put("vehilceEmission",Msg.IS_NOT_VALID.get("vehilceEmission"));
		
	}

	@Override
	public void validVehicleType(String vehicleType) {
		if(CheckUtils.invalidVehicleEmission(vehicleType))
			errors.put("vehicleType",Msg.IS_NOT_VALID.get("vehicleType"));
		
	}

	@Override
	public void validVehiclePlate(String vehiclePlate) {
		if(CheckUtils.invalidVehiclePlate(vehiclePlate))
			errors.put("vehiclePlate",Msg.IS_NOT_VALID.get("vehiclePlate"));
		
	}

}
