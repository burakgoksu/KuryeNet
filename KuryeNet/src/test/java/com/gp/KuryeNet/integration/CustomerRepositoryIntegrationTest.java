package com.gp.KuryeNet.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import com.gp.KuryeNet.core.config.AuditingConfig;
import com.gp.KuryeNet.dataAccess.abstracts.AddressDao;
import com.gp.KuryeNet.dataAccess.abstracts.CustomerDao;
import com.gp.KuryeNet.entities.concretes.Address;
import com.gp.KuryeNet.entities.concretes.Customer;

@DataJpaTest
@Import(AuditingConfig.class)
class CustomerRepositoryIntegrationTest {

    @Autowired
    private CustomerDao customerDao;

    @Autowired
    private AddressDao addressDao;

    @Test
    void findAll_shouldExcludeSoftDeletedRows() {
        Address address = buildAddress();
        addressDao.save(address);

        Customer activeCustomer = buildCustomer("active@gmail.com");
        activeCustomer.setCustomerAddress(address);

        Customer deletedCustomer = buildCustomer("deleted@gmail.com");
        deletedCustomer.setCustomerAddress(address);
        deletedCustomer.setDeleted(true);

        customerDao.save(activeCustomer);
        customerDao.save(deletedCustomer);

        List<Customer> active = customerDao.findAll();
        List<Customer> deleted = customerDao.getDeleted();

        assertEquals(1, active.size());
        assertEquals("active@gmail.com", active.get(0).getCustomerEmail());
        assertEquals(1, deleted.size());
        assertEquals("deleted@gmail.com", deleted.get(0).getCustomerEmail());
    }

    private Customer buildCustomer(String email) {
        Customer customer = new Customer();
        customer.setCustomerName("Ali");
        customer.setCustomerSurname("Demir");
        customer.setCustomerEmail(email);
        customer.setCustomerBirthday(new Date());
        return customer;
    }

    private Address buildAddress() {
        Address address = new Address();
        address.setAddress("Ataturk Mahallesi");
        address.setAddressTitle("Home");
        address.setCity("Manisa");
        address.setDistrict("Yunus Emre");
        address.setStreet("Muradiye");
        address.setBuildingNumber("2");
        address.setFloorNumber("5");
        address.setApartmentNumber("12");
        address.setPhoneNumber("05458624532");
        return address;
    }
}
