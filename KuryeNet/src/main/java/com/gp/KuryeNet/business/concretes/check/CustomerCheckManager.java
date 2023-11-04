package com.gp.KuryeNet.business.concretes.check;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.check.CustomerCheckService;
import com.gp.KuryeNet.core.utulities.Util.CheckUtils;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.core.utulities.exception.exceptions.EntityNotExistsException;
import com.gp.KuryeNet.dataAccess.abstracts.CustomerDao;

import lombok.SneakyThrows;

@Service
public class CustomerCheckManager extends BaseCheckManager implements CustomerCheckService{

	private CustomerDao customerDao;
	
	@Autowired
	public CustomerCheckManager(CustomerDao customerDao) {
		super();
		this.customerDao = customerDao;
	}

	@SneakyThrows
	@Override
	public void existsCustomerById(int customerId) {
		if(CheckUtils.notExistsById(customerDao, customerId))
			throw new EntityNotExistsException(Msg.NOT_EXIST.get("Customer"));
		
	}

	@Override
	public void existsByCustomerEmail(String customerEmail) {
		if(customerDao.existsByCustomerEmail(customerEmail))
			errors.put("customerEmail", Msg.IS_IN_USE.get());
		
	}

	@Override
	public void validEmail(String customerEmail) {
		if(CheckUtils.invalidEmail(customerEmail))
			errors.put("customerEmail", Msg.IS_NOT_VALID.get());
		
	}

}
