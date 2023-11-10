package com.gp.KuryeNet.core.business.abstracts;

import java.util.ArrayList;

import com.gp.KuryeNet.core.entities.DirectionsResponse;
import com.gp.KuryeNet.core.utulities.result.DataResult;

public interface GoogleMapsAPIService {
	
	DataResult<Integer> getDirectionsFromGoogleMaps(int courierId,int customerId);
	
}
