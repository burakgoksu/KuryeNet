package com.gp.KuryeNet.core.business.abstracts;

import com.gp.KuryeNet.core.utulities.result.Result;

public interface OpenWeatherMapService {
	
	Result getWeather(String courierEmail);

}
