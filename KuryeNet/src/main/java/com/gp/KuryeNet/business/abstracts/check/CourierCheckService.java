package com.gp.KuryeNet.business.abstracts.check;

import java.util.Map;

public interface CourierCheckService extends BaseCheckService{
	
	Map<String, String> getErrors();
	
    void existsCourierById(int courierId);
    
    void existsByCourierIdentityNumber(String courierIdentityNumber);
    
    void existsByCourierEmail(String courierEmail);
    
    void validEmail(String courierEmail);
    
    void validIdentityNumber(String courierIdentityNumber);
    
    void availableCourier(int courierId);
    
    void distributionCourier(int courierId);

}