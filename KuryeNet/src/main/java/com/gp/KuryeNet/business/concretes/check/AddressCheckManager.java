package com.gp.KuryeNet.business.concretes.check;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.check.AddressCheckService;
import com.gp.KuryeNet.core.utulities.Util.CheckUtils;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.core.utulities.exception.exceptions.EntityNotExistsException;
import com.gp.KuryeNet.dataAccess.abstracts.AddressDao;

import lombok.SneakyThrows;

@Service
public class AddressCheckManager  extends BaseCheckManager implements AddressCheckService{
	
	private AddressDao addressDao;
	
	@Autowired
	public AddressCheckManager(AddressDao addressDao) {
		super();
		this.addressDao = addressDao;
	}

	@SneakyThrows
	@Override
	public void existsAddressById(int addressId) {
		 if (CheckUtils.notExistsById(addressDao, addressId))
	            throw new EntityNotExistsException(Msg.NOT_EXIST.get("Address"));
		
	}

	@Override
	public void existsPhoneNumber(String phoneNumber) {
		if (addressDao.existsByPhoneNumber(phoneNumber))
			errors.put("phoneNumber", Msg.IS_IN_USE.get("phoneNumber"));
		
	}

	@Override
	public void validPhoneNumber(String phoneNumber) {
		if (CheckUtils.invalidPhone(phoneNumber))
			errors.put("phoneNumber",Msg.IS_NOT_VALID.get());
		
	}

}	
