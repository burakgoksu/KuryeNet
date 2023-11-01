package com.gp.KuryeNet.business.concretes;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.CustomerService;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;
import com.gp.KuryeNet.dataAccess.abstracts.CustomerDao;
import com.gp.KuryeNet.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService{
	
	private CustomerDao customerDao;

	@Autowired
	public CustomerManager(CustomerDao customerDao) {
		super();
		this.customerDao = customerDao;
	}

	@Override
	public DataResult<List<Customer>> getAll() {
		return new SuccessDataResult<List<Customer>>(this.customerDao.findAll());
	}

	@Override
	public DataResult<List<Customer>> getAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1,pageSize);
		return new SuccessDataResult<List<Customer>>(this.customerDao.findAll(pageable).getContent());
	}

	@Override
	public DataResult<List<Customer>> getAllSortedByCustomerName() {
		Sort sort = Sort.by(Sort.Direction.ASC,"customerName");
		return new SuccessDataResult<List<Customer>>(this.customerDao.findAll(sort),"Name, Customer listed");
	}

	@Override
	public Result add(Customer customer) {
		this.customerDao.save(customer);
		return new SuccessResult("customer added");
	}

	@Override
	public DataResult<Customer> getByCustomerId(int customerId) {
		return new SuccessDataResult<Customer>(this.customerDao.getByCustomerId(customerId));
	}

	@Override
	public DataResult<Customer> getByCustomerNameAndCustomerSurname(String customerName, String customerSurname) {
		return new SuccessDataResult<Customer>(this.customerDao.getByCustomerNameAndCustomerSurname(customerName,customerSurname));

	}

	@Override
	public DataResult<Customer> getByCustomerEmail(String customerEmail) {
		return new SuccessDataResult<Customer>(this.customerDao.getByCustomerEmail(customerEmail));

	}

	@Override
	public DataResult<Customer> getByCustomerAddress_AddressId(int addressId) {
		return new SuccessDataResult<Customer>(this.customerDao.getByCustomerAddress_AddressId(addressId));

	}

	@Override
	public DataResult<List<Customer>> getByCustomerAddress_City(String city) {
		return new SuccessDataResult<List<Customer>>(this.customerDao.getByCustomerAddress_City(city));

	}

}
