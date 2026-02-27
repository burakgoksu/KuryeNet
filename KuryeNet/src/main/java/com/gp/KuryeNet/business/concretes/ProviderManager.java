package com.gp.KuryeNet.business.concretes;

import java.util.List;
import java.time.Instant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.ProviderService;
import com.gp.KuryeNet.business.abstracts.check.ProviderCheckService;
import com.gp.KuryeNet.core.entities.ApiError;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorDataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;
import com.gp.KuryeNet.core.utulities.security.SecurityUtils;
import com.gp.KuryeNet.dataAccess.abstracts.ProviderDao;
import com.gp.KuryeNet.entities.concretes.Order;
import com.gp.KuryeNet.entities.concretes.Provider;

@Service
public class ProviderManager implements ProviderService{
	
	private ProviderDao providerDao;
	private ProviderCheckService providerCheckService;
	
	@Autowired
	public ProviderManager(ProviderDao providerDao,ProviderCheckService providerCheckService) {
		super();
		this.providerDao = providerDao;
		this.providerCheckService = providerCheckService;
	}

	@Override
	@Cacheable(cacheNames = "providersAll")
	public DataResult<List<Provider>> getAll() {
		return new SuccessDataResult<List<Provider>>(this.providerDao.findAll(),"Provider Data Listed");
	}

	@Override
	public DataResult<List<Provider>> getAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1,pageSize);
		return new SuccessDataResult<List<Provider>>(this.providerDao.findAll(pageable).getContent(),"Provider Data Listed");
	}

	@Override
	@Cacheable(cacheNames = "providersSortedByName")
	public DataResult<List<Provider>> getAllSortedByProviderName() {
		Sort sort = Sort.by(Sort.Direction.ASC,"providerName");
		return new SuccessDataResult<List<Provider>>(this.providerDao.findAll(sort),"ASC providers listed successfully");
	}

	@Override
	@Cacheable(cacheNames = "providersByName", key = "#providerName")
	public DataResult<Provider> getByProviderName(String providerName) {
		return new SuccessDataResult<Provider>(this.providerDao.getByProviderName(providerName));
	}

	@Override
	@Cacheable(cacheNames = "providersByType", key = "#providerType")
	public DataResult<List<Provider>> getByProviderType(String providerType) {
		return new SuccessDataResult<List<Provider>>(this.providerDao.getByProviderType(providerType));

	}

	@Override
	@Cacheable(cacheNames = "providersByAddressId", key = "#addressId")
	public DataResult<Provider> getByProviderAddress_AddressId(int addressId) {
		return new SuccessDataResult<Provider>(this.providerDao.getByProviderAddress_AddressId(addressId));

	}

	@Override
	@Cacheable(cacheNames = "providersByCity", key = "#addressCity")
	public DataResult<List<Provider>> getByProviderAddress_City(String addressCity) {
		return new SuccessDataResult<List<Provider>>(this.providerDao.getByProviderAddress_City(addressCity));

	}

	@Override
	@Cacheable(cacheNames = "providersByDistrict", key = "#addressDistrict")
	public DataResult<List<Provider>> getByProviderAddress_District(String addressDistrict) {
		return new SuccessDataResult<List<Provider>>(this.providerDao.getByProviderAddress_District(addressDistrict));

	}

	@Override
	@Cacheable(cacheNames = "providersByCityDistrict", key = "#addressCity + ':' + #addressDistrict")
	public DataResult<List<Provider>> getByProviderAddress_CityAndProviderAddress_District(String addressCity, String addressDistrict) {
		return new SuccessDataResult<List<Provider>>(this.providerDao.getByProviderAddress_CityAndProviderAddress_District(addressCity,addressDistrict));

	}

	@Override
	@CacheEvict(cacheNames = {"providersAll","providersSortedByName","providersByName","providersByType","providersByAddressId","providersByCity","providersByDistrict","providersByCityDistrict","providersById","providersByMersis"}, allEntries = true)
	public Result add(Provider provider) {
		providerCheckService.existsByProviderMersisNo(provider.getProviderMersisNo());
		providerCheckService.validProviderMersisNo(provider.getProviderMersisNo());
		ErrorDataResult<ApiError> errors = Utils.getErrorsIfExist(providerCheckService);
		if(errors!=null) return errors;
		else this.providerDao.save(provider);
		return new SuccessResult("Provider added");
	}

	@Override
	@Cacheable(cacheNames = "providersById", key = "#providerId")
	public DataResult<Provider> getByProviderId(int providerId) {
		providerCheckService.existsProviderById(providerId);
		return new SuccessDataResult<Provider>(this.providerDao.getByProviderId(providerId));
	}

	@Override
	@Cacheable(cacheNames = "providersByMersis", key = "#providerMersisNo")
	public DataResult<Provider> getByProviderMersisNo(String providerMersisNo) {
		return new SuccessDataResult<Provider>(this.providerDao.getByProviderMersisNo(providerMersisNo));
	}

	@Override
	@CacheEvict(cacheNames = {"providersAll","providersSortedByName","providersByName","providersByType","providersByAddressId","providersByCity","providersByDistrict","providersByCityDistrict","providersById","providersByMersis"}, allEntries = true)
	public Result delete(int providerId) {
		providerCheckService.existsProviderById(providerId);
		ErrorDataResult<ApiError> errors = Utils.getErrorsIfExist(providerCheckService);
		if(errors!=null) return errors;

		int updated = providerDao.softDeleteById(providerId, Instant.now(), SecurityUtils.resolveCurrentUser());
		if (updated == 0) {
			return new ErrorResult(Msg.NOT_FOUND.get());
		}
		return new SuccessResult("provider deleted");
	}

	@Override
	@CacheEvict(cacheNames = {"providersAll","providersSortedByName","providersByName","providersByType","providersByAddressId","providersByCity","providersByDistrict","providersByCityDistrict","providersById","providersByMersis"}, allEntries = true)
	public Result restore(int providerId) {
		int updated = providerDao.restoreById(providerId);
		if (updated == 0) {
			return new ErrorResult(Msg.NOT_FOUND.get());
		}
		return new SuccessResult("provider restored");
	}

	@Override
	public DataResult<List<Provider>> getDeleted() {
		return new SuccessDataResult<List<Provider>>(providerDao.getDeleted());
	}

}
