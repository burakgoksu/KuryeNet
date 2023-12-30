package com.gp.KuryeNet.core.business.abstracts;

import java.util.ArrayList;

import com.gp.KuryeNet.core.entities.DirectionsResponse;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;

public interface GoogleMapsAPIService {
	
	Result getRemainingDataFromGoogleMaps(String courierEmail,int orderId);
	
	Result getDirectionsFromGoogleMaps(String courierEmail, int orderId);
	
}
