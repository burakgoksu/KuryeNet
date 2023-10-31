package com.gp.KuryeNet.business.abstracts;

import java.util.List;

import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.entities.concretes.Address;

public interface AddressService {
	
	DataResult<List<Address>> getAll();
	
	DataResult<List<Address>> getAll(int pageNo, int pageSize);
	
	DataResult<List<Address>> getAllSortedByCity();

	Result add(Address address);
	
	DataResult<List<Address>> getByAddressTitle(String addressTitle);
	
	DataResult<List<Address>> getByPhoneNumber(String phoneNumber);
	
	DataResult<List<Address>> getByCity(String city);
}
