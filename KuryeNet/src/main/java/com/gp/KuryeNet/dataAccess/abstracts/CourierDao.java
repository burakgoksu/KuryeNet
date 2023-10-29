package com.gp.KuryeNet.dataAccess.abstracts;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.gp.KuryeNet.entities.concretes.Courier;


@Repository
@Component
public interface CourierDao extends JpaRepository<Courier,Integer>{
	
	Courier getByCourierNameAndCourierSurname(String courierName, String courierSurname);
	
	Courier getByCourierIdentityNumber(String identityNumber);
	
	Courier getByCourierEmail(String email);
	
}
