package com.gp.KuryeNet.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.ProviderService;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;
import com.gp.KuryeNet.dataAccess.abstracts.ProviderDao;
import com.gp.KuryeNet.entities.concretes.Order;
import com.gp.KuryeNet.entities.concretes.Provider;

@Service
public class ProviderManager implements ProviderService{
	
	private ProviderDao providerDao;
	
	@Autowired
	public ProviderManager(ProviderDao providerDao) {
		super();
		this.providerDao = providerDao;
	}

	@Override
	public DataResult<List<Provider>> getAll() {
		return new SuccessDataResult<List<Provider>>(this.providerDao.findAll(),"Provider Data Listed");
	}

	@Override
	public DataResult<List<Provider>> getAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1,pageSize);
		return new SuccessDataResult<List<Provider>>(this.providerDao.findAll(pageable).getContent(),"Provider Data Listed");
	}

	@Override
	public DataResult<List<Provider>> getAllSortedByProviderName() {
		Sort sort = Sort.by(Sort.Direction.ASC,"providerName");
		return new SuccessDataResult<List<Provider>>(this.providerDao.findAll(sort),"ASC providers listed successfully");
	}

	@Override
	public DataResult<Provider> getByProviderName(String providerName) {
		return new SuccessDataResult<Provider>(this.providerDao.getByProviderName(providerName));
	}

	@Override
	public DataResult<List<Provider>> getByProviderType(String providerType) {
		return new SuccessDataResult<List<Provider>>(this.providerDao.getByProviderType(providerType));

	}

	@Override
	public DataResult<Provider> getByProviderAddress_AddressId(int addressId) {
		return new SuccessDataResult<Provider>(this.providerDao.getByProviderAddress_AddressId(addressId));

	}

	@Override
	public DataResult<List<Provider>> getByProviderAddress_City(String addressCity) {
		return new SuccessDataResult<List<Provider>>(this.providerDao.getByProviderAddress_City(addressCity));

	}

	@Override
	public DataResult<List<Provider>> getByProviderAddress_District(String addressDistrict) {
		return new SuccessDataResult<List<Provider>>(this.providerDao.getByProviderAddress_District(addressDistrict));

	}

	@Override
	public DataResult<List<Provider>> getByProviderAddress_CityAndProviderAddress_District(String addressCity, String addressDistrict) {
		return new SuccessDataResult<List<Provider>>(this.providerDao.getByProviderAddress_CityAndProviderAddress_District(addressCity,addressDistrict));

	}

	@Override
	public Result add(Provider provider) {
		this.providerDao.save(provider);
		return new SuccessResult("Provider added");
	}

}
