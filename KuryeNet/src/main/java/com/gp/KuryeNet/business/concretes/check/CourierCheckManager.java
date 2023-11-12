package com.gp.KuryeNet.business.concretes.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.check.CourierCheckService;
import com.gp.KuryeNet.core.utulities.Util.CheckUtils;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.core.utulities.exception.exceptions.EntityNotExistsException;
import com.gp.KuryeNet.dataAccess.abstracts.CourierDao;
import com.gp.KuryeNet.entities.concretes.Courier;

import lombok.SneakyThrows;

@Service
public class CourierCheckManager extends BaseCheckManager implements CourierCheckService {

	private CourierDao courierDao;
	
	@Autowired
	public CourierCheckManager(CourierDao courierDao) {
		super();
		this.courierDao = courierDao;
	}

	@SneakyThrows
	@Override
	public void existsCourierById(int courierId) {
		if(CheckUtils.notExistsById(courierDao, courierId))
			throw new EntityNotExistsException(Msg.NOT_EXIST.get("Courier"));
		
	}

	@Override
	public void existsByCourierIdentityNumber(String courierIdentityNumber) {
		if(courierDao.existsByCourierIdentityNumber(courierIdentityNumber))
			errors.put("courierIdentityNumber", Msg.IS_IN_USE.get());
	}

	@Override
	public void existsByCourierEmail(String courierEmail) {
		if(courierDao.existsByCourierEmail(courierEmail))
			errors.put("courierEmail", Msg.IS_IN_USE.get());
	}

	@Override
	public void validEmail(String courierEmail) {
		if(CheckUtils.invalidEmail(courierEmail))
			errors.put("courierEmail", Msg.IS_NOT_VALID.get());
	}

	@Override
	public void validIdentityNumber(String courierIdentityNumber) {
		if(CheckUtils.invalidIdentityNumber(courierIdentityNumber))
			errors.put("courierIdentityNumber", Msg.IS_NOT_VALID.get());
		
	}
	
	@Override
	public void availableCourier(String courierEmail) {
		if(courierDao.getByCourierEmail(courierEmail).getCourierStatus() != 100)
			errors.put("courierId", Msg.IS_IN_DISTRIBUTION.get());
		
	}
	
	@Override
	public void distributionCourier(String courierEmail) {
		if(courierDao.getByCourierEmail(courierEmail).getCourierStatus() != 200)
			errors.put("courierId", Msg.IS_NOT_IN_DISTRIBUTION.get());
		
	}

	@Override
	public void existsCourierByEmail(String courierEmail) {
		// TODO Auto-generated method stub
		
	}




}
