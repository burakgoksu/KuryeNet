package com.gp.KuryeNet.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.AddressService;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;
import com.gp.KuryeNet.dataAccess.abstracts.AddressDao;
import com.gp.KuryeNet.entities.concretes.Address;

@Service
public class AddressManager implements AddressService{
	
	private AddressDao addressDao;

	@Autowired
	public AddressManager(AddressDao addressDao) {
		super();
		this.addressDao = addressDao;
	}
	
	@Override
	public DataResult<List<Address>> getAll() {
		return new SuccessDataResult<List<Address>>(this.addressDao.findAll(),"Address Data Listed");
	}

	@Override
	public DataResult<List<Address>> getAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1,pageSize);
		return new SuccessDataResult<List<Address>>(this.addressDao.findAll(pageable).getContent());
	}

	@Override
	public DataResult<List<Address>> getAllSortedByCity() {
		Sort sort = Sort.by(Sort.Direction.ASC,"city");
		return new SuccessDataResult<List<Address>>(this.addressDao.findAll(sort),"ASC Address listed successfully");
	}

	@Override
	public Result add(Address address) {
		this.addressDao.save(address);
		return new SuccessResult("Address added");
	}

	@Override
	public DataResult<List<Address>> getByAddressTitle(String addressTitle) {
		return new SuccessDataResult<List<Address>>(this.addressDao.getByAddressTitle(addressTitle));
	}

	@Override
	public DataResult<List<Address>> getByPhoneNumber(String phoneNumber) {
		return new SuccessDataResult<List<Address>>(this.addressDao.getByPhoneNumber(phoneNumber));

	}

	@Override
	public DataResult<List<Address>> getByCity(String city) {
		return new SuccessDataResult<List<Address>>(this.addressDao.getByCity(city));

	}

}
