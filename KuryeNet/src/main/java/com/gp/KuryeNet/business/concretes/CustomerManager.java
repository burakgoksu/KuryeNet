package com.gp.KuryeNet.business.concretes;

import java.util.List;
import java.time.Instant;
import java.util.Optional;
import java.util.function.Consumer;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.gp.KuryeNet.business.abstracts.CustomerService;
import com.gp.KuryeNet.business.abstracts.check.CustomerCheckService;
import com.gp.KuryeNet.core.entities.ApiError;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorDataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.core.utulities.result.SuccessResult;
import com.gp.KuryeNet.core.utulities.security.SecurityUtils;
import com.gp.KuryeNet.core.utulities.Util.Msg;
import com.gp.KuryeNet.dataAccess.abstracts.CustomerDao;
import com.gp.KuryeNet.entities.concretes.Address;
import com.gp.KuryeNet.entities.concretes.Customer;

@Service
public class CustomerManager implements CustomerService{
	
	private CustomerDao customerDao;
	private CustomerCheckService customerCheckService;
	
    @PersistenceContext
    private EntityManager entityManager;

	@Autowired
	public CustomerManager(CustomerDao customerDao,CustomerCheckService customerCheckService, EntityManager entityManager) {
		super();
		this.customerDao = customerDao;
		this.customerCheckService = customerCheckService;
		this.entityManager = entityManager;
	}

	@Override
	@Cacheable(cacheNames = "customersAll")
	public DataResult<List<Customer>> getAll() {
		return new SuccessDataResult<List<Customer>>(this.customerDao.findAll());
	}

	@Override
	public DataResult<List<Customer>> getAll(int pageNo, int pageSize) {
		Pageable pageable = PageRequest.of(pageNo-1,pageSize);
		return new SuccessDataResult<List<Customer>>(this.customerDao.findAll(pageable).getContent());
	}

	@Override
	@Cacheable(cacheNames = "customersSortedByName")
	public DataResult<List<Customer>> getAllSortedByCustomerName() {
		Sort sort = Sort.by(Sort.Direction.ASC,"customerName");
		return new SuccessDataResult<List<Customer>>(this.customerDao.findAll(sort),"Name, Customer listed");
	}

	@Override
	@CacheEvict(cacheNames = {"customersAll","customersSortedByName","customersById","customersByEmail","customersByNameSurname","customersByAddressId","customersByCity"}, allEntries = true)
	public Result add(Customer customer) {
		customerCheckService.existsByCustomerEmail(customer.getCustomerEmail());
		customerCheckService.validEmail(customer.getCustomerEmail());
		ErrorDataResult<ApiError> errors= Utils.getErrorsIfExist(customerCheckService);
		if(errors!=null) return errors;
		else this.customerDao.save(customer);
		
		return new SuccessResult("customer added");
	}
	
	public <T> void updateIfNotNull(Consumer<T> setter, T value) {
	    Optional.ofNullable(value).ifPresent(setter);
	}
	
	@Override
	@Transactional
	@CacheEvict(cacheNames = {"customersAll","customersSortedByName","customersById","customersByEmail","customersByNameSurname","customersByAddressId","customersByCity"}, allEntries = true)
	public Result update(String customerEmail,Customer customer) {
		//customerCheckService.existsByCustomerEmail(customer.getCustomerEmail());
		//ErrorDataResult<ApiError> errors= Utils.getErrorsIfExist(customerCheckService);
		//if(errors!=null) return errors;
		//else 
		
		if (customer.getCustomerAddress() != null && customer.getCustomerAddress().getAddressId() != 0) {
            Address mergedAddress = entityManager.merge(customer.getCustomerAddress());
            customer.setCustomerAddress(mergedAddress);
        }

	    Customer oldCustomer = customerDao.getByCustomerEmail(customerEmail);
	    
	    updateIfNotNull(oldCustomer::setCustomerName, customer.getCustomerName());
	    updateIfNotNull(oldCustomer::setCustomerSurname, customer.getCustomerSurname());
	    updateIfNotNull(oldCustomer::setCustomerBirthday, customer.getCustomerBirthday());
	    updateIfNotNull(oldCustomer::setCustomerAddress, customer.getCustomerAddress());
	    updateIfNotNull(oldCustomer::setCustomerLatitude, customer.getCustomerLatitude());
	    updateIfNotNull(oldCustomer::setCustomerLongitude, customer.getCustomerLongitude());
	    updateIfNotNull(oldCustomer::setCustomerEmail, customer.getCustomerEmail());

	    if (customer.getOrders() != null && !customer.getOrders().isEmpty()) {
	        oldCustomer.setOrders(customer.getOrders());
	    }

		this.customerDao.save(oldCustomer);
		
		return new SuccessResult("customer updated");
	}

	@Override
	@Cacheable(cacheNames = "customersById", key = "#customerId")
	public DataResult<Customer> getByCustomerId(int customerId) {
		customerCheckService.existsCustomerById(customerId);
		return new SuccessDataResult<Customer>(this.customerDao.getByCustomerId(customerId));
	}

	@Override
	@Cacheable(cacheNames = "customersByNameSurname", key = "#customerName + ':' + #customerSurname")
	public DataResult<Customer> getByCustomerNameAndCustomerSurname(String customerName, String customerSurname) {
		return new SuccessDataResult<Customer>(this.customerDao.getByCustomerNameAndCustomerSurname(customerName,customerSurname));

	}

	@Override
	@Cacheable(cacheNames = "customersByEmail", key = "#customerEmail")
	public DataResult<Customer> getByCustomerEmail(String customerEmail) {
		return new SuccessDataResult<Customer>(this.customerDao.getByCustomerEmail(customerEmail));

	}

	@Override
	@Cacheable(cacheNames = "customersByAddressId", key = "#addressId")
	public DataResult<Customer> getByCustomerAddress_AddressId(int addressId) {
		return new SuccessDataResult<Customer>(this.customerDao.getByCustomerAddress_AddressId(addressId));

	}

	@Override
	@Cacheable(cacheNames = "customersByCity", key = "#city")
	public DataResult<List<Customer>> getByCustomerAddress_City(String city) {
		return new SuccessDataResult<List<Customer>>(this.customerDao.getByCustomerAddress_City(city));

	}

	@Override
	@CacheEvict(cacheNames = {"customersAll","customersSortedByName","customersById","customersByEmail","customersByNameSurname","customersByAddressId","customersByCity"}, allEntries = true)
	public Result delete(int customerId) {
		customerCheckService.existsCustomerById(customerId);
		ErrorDataResult<ApiError> errors= Utils.getErrorsIfExist(customerCheckService);
		if(errors!=null) return errors;

		int updated = customerDao.softDeleteById(customerId, Instant.now(), SecurityUtils.resolveCurrentUser());
		if (updated == 0) {
			return new ErrorResult(Msg.NOT_FOUND.get());
		}
		return new SuccessResult("customer deleted");
	}

	@Override
	@CacheEvict(cacheNames = {"customersAll","customersSortedByName","customersById","customersByEmail","customersByNameSurname","customersByAddressId","customersByCity"}, allEntries = true)
	public Result restore(int customerId) {
		int updated = customerDao.restoreById(customerId);
		if (updated == 0) {
			return new ErrorResult(Msg.NOT_FOUND.get());
		}
		return new SuccessResult("customer restored");
	}

	@Override
	public DataResult<List<Customer>> getDeleted() {
		return new SuccessDataResult<List<Customer>>(customerDao.getDeleted());
	}

}
