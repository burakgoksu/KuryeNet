package com.gp.KuryeNet.business.abstracts.check;

import java.util.Map;

public interface VehicleCheckService extends BaseCheckService{
	
	Map<String, String> getErrors();
	
    void existsVehicleById(int vehicleId);
    
    void existsByVehiclePlate(String vehiclePlate);
    
    void validVehicleEmission(String vehilceEmission);
    
    void validVehicleType(String vehicleType);
    
    void validVehiclePlate(String vehiclePlate);

}
