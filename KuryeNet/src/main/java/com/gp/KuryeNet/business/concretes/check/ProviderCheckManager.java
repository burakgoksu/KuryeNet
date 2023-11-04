package com.gp.KuryeNet.business.concretes.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.check.ProviderCheckService;
import com.gp.KuryeNet.core.utulities.Util.CheckUtils;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.core.utulities.exception.exceptions.EntityNotExistsException;
import com.gp.KuryeNet.dataAccess.abstracts.ProviderDao;

import lombok.SneakyThrows;

@Service
public class ProviderCheckManager extends BaseCheckManager implements ProviderCheckService{
	
	private ProviderDao providerDao;

	@Autowired
	public ProviderCheckManager(ProviderDao providerDao) {
		super();
		this.providerDao = providerDao;
	}

	@Override
	@SneakyThrows
	public void existsProviderById(int providerId) {
		if(CheckUtils.notExistsById(providerDao, providerId))
			throw new EntityNotExistsException(Msg.NOT_EXIST.get("Provider"));
		
	}

	@Override
	public void existsByProviderMersisNo(String providerMersisNo) {
		if(providerDao.existsByProviderMersisNo(providerMersisNo))
			errors.put("providerMersisNo", Msg.IS_IN_USE.get("ProviderMersisNo"));
		
	}

	@Override
	public void validProviderMersisNo(String providerMersisNo) {
		if(CheckUtils.invalidProviderMersisNo(providerMersisNo))
			errors.put("providerMersisNo", Msg.IS_NOT_VALID.get("ProviderMersisNo"));
		
	}

}
