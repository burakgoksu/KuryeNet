package com.gp.KuryeNet.business.abstracts;

import java.util.List;

import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.entities.concretes.Provider;

public interface ProviderService {
	
	DataResult<List<Provider>> getAll();
	
	DataResult<List<Provider>> getAll(int pageNo, int pageSize);
	
	DataResult<List<Provider>> getAllSortedByProviderName();
	
	Result add(Provider provider);
	
	DataResult<Provider> getByProviderName(String providerName);
	
	DataResult<List<Provider>> getByProviderType(String providerType);
	
	DataResult<Provider> getByProviderAddress_AddressId(int addressId);
	
	DataResult<List<Provider>> getByProviderAddress_City(String addressCity);
	
	DataResult<List<Provider>> getByProviderAddress_District(String addressDistrict);
	
	DataResult<List<Provider>> getByProviderAddress_CityAndProviderAddress_District(String addressCity, String addressDistrict);

}
