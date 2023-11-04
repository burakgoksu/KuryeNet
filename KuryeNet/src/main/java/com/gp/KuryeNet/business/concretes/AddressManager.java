package com.gp.KuryeNet.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.AddressService;
import com.gp.KuryeNet.business.abstracts.check.AddressCheckService;
import com.gp.KuryeNet.core.entities.ApiError;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorDataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;
import com.gp.KuryeNet.dataAccess.abstracts.AddressDao;
import com.gp.KuryeNet.entities.concretes.Address;

@Service
public class AddressManager implements AddressService{
	
	private AddressDao addressDao;
	private AddressCheckService addressCheckService;

	@Autowired
	public AddressManager(AddressDao addressDao,AddressCheckService addressCheckService) {
		super();
		this.addressDao = addressDao;
		this.addressCheckService = addressCheckService;
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
		addressCheckService.existsPhoneNumber(address.getPhoneNumber());
		addressCheckService.validPhoneNumber(address.getPhoneNumber());
		ErrorDataResult<ApiError> errors = Utils.getErrorsIfExist(addressCheckService);
	    if (errors != null) return errors;
		
		return new SuccessResult(Msg.SAVED.get());
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

	@Override
	public DataResult<Address> getByAddressId(int addressId) {
		addressCheckService.existsAddressById(addressId);
		return new SuccessDataResult<Address>(this.addressDao.getByAddressId(addressId));
	}

}
