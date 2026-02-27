package com.gp.KuryeNet.core.business.concretes;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gp.KuryeNet.core.business.abstracts.OpenWeatherMapService;
import com.gp.KuryeNet.core.clients.ExternalApiClient;
import com.gp.KuryeNet.core.entities.WeatherResponse;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.ErrorDataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.dataAccess.abstracts.CourierDao;
import com.gp.KuryeNet.entities.concretes.Courier;

@Service
public class OpenWeatherMapManager implements OpenWeatherMapService{
	
	private CourierDao courierDao;
	private ExternalApiClient externalApiClient;

	@Autowired
	public OpenWeatherMapManager(CourierDao courierDao, ExternalApiClient externalApiClient) {
		super();
		this.courierDao = courierDao;
		this.externalApiClient = externalApiClient;
	}

	@Async
	@Transactional
	@Override
	public Result getWeather(String courierEmail) {
		
	
		WeatherResponse weatherResponse = new WeatherResponse();
		Courier courier = courierDao.getByCourierEmail(courierEmail);
		double courierlat = courier.getCourierLatitude();
		double courierlong = courier.getCourierLongitude();
		
        HttpHeaders headers = new HttpHeaders();

        String url = "https://api.openweathermap.org/data/3.0/onecall";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("lat", courierlat)
                .queryParam("lon", courierlong)
                .queryParam("appid", Utils.Const.OPEN_WEATHER_MAP_KEY);


        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = externalApiClient.getOpenWeather(builder.toUriString(), requestEntity);
        
        String weatherCondition = null;
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        // Handle the response
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            
            
            try {
                
                JsonNode rootNode = objectMapper.readTree(responseBody);
                JsonNode current = rootNode.get("current");
                
                if (current != null) {
                    JsonNode weather = current.get("weather");
                   
                    if (weather != null && weather.isArray() && weather.size() > 0) {
                        JsonNode main = weather.get(0).get("main");
                        
                        weatherCondition = main.asText();
                    }
                }
            }
            
            catch (Exception e) {
                e.printStackTrace();
            }
                        
            weatherResponse.setWeatherCondition(weatherCondition);

        } 
       
        else {
        	return new ErrorDataResult<>("Request failed with status code: " + response.getStatusCode());
        }
        
        return new SuccessDataResult<WeatherResponse>(weatherResponse,"Kurye Konumuna Göre Hava Durumu Alınmıştır.");
        
	} 
                  
}


                
