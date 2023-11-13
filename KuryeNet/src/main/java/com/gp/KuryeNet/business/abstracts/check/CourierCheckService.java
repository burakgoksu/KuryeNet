package com.gp.KuryeNet.business.abstracts.check;

import java.util.Map;

public interface CourierCheckService extends BaseCheckService{
	
	Map<String, String> getErrors();
	
    void existsCourierById(int courierId);
    
    void existsCourierByEmail(String courierEmail);
    
    void existsByCourierIdentityNumber(String courierIdentityNumber);
    
    void existsByCourierEmail(String courierEmail);
    
    void existsInUserByEmail(String courierEmail);
    
    void validEmail(String courierEmail);
    
    void validIdentityNumber(String courierIdentityNumber);
    
    void availableCourier(String courierEmail);
    
    void distributionCourier(String courierEmail);

}
