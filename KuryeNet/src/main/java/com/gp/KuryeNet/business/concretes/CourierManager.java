package com.gp.KuryeNet.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.CourierService;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;
import com.gp.KuryeNet.dataAccess.abstracts.CourierDao;
import com.gp.KuryeNet.entities.concretes.Courier;

@Service
public class CourierManager implements CourierService{
	
	private CourierDao courierDao;
	
	@Autowired
	public CourierManager(CourierDao courierDao) {
		super();
		this.courierDao = courierDao;
	}

	@Override
	public DataResult<List<Courier>> getAll() {
		return new SuccessDataResult<List<Courier>>(this.courierDao.findAll());
	}

	@Override
	public Result add(Courier courier) {
		this.courierDao.save(courier);
		return new SuccessResult("courier added");
	}

	@Override
	public DataResult<Courier> getByCourierNameAndCourierSurname(String courierName, String courierSurname) {
		return new SuccessDataResult<Courier>(this.courierDao.getByCourierNameAndCourierSurname(courierName, courierSurname));
	}

	@Override
	public DataResult<Courier> getByCourierIdentityNumber(String courierIdentityNumber) {
		return new SuccessDataResult<Courier>(this.courierDao.getByCourierIdentityNumber(courierIdentityNumber));

	}

	@Override
	public DataResult<Courier> getByCourierEmail(String courierEmail) {
		return new SuccessDataResult<Courier>(this.courierDao.getByCourierEmail(courierEmail));

	}

	@Override
	public DataResult<List<Courier>> getAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1,pageSize);
		return new SuccessDataResult<List<Courier>>(this.courierDao.findAll(pageable).getContent());
	}

	@Override
	public DataResult<List<Courier>> getAllSortedByCourierName() {
		Sort sort = Sort.by(Sort.Direction.ASC,"courierName");
		return new SuccessDataResult<List<Courier>>(this.courierDao.findAll(sort),"ASC Couriers listed successfully");
	}

}
