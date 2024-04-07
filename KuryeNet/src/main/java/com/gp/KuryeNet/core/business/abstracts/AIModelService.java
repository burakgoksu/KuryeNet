package com.gp.KuryeNet.core.business.abstracts;

import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.entities.AIModelPredictionRequest;
import com.gp.KuryeNet.core.entities.AIModelPredictionResponse;


public interface AIModelService {
	
	DataResult<AIModelPredictionResponse> getPrediction(String courierEmail,int orderId);
	
	Result writeData(String courierEmail, int orderId);

}
