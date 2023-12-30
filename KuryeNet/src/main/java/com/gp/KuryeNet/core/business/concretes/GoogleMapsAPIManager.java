package com.gp.KuryeNet.core.business.concretes;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.transaction.Transactional;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gp.KuryeNet.core.business.abstracts.GoogleMapsAPIService;
import com.gp.KuryeNet.core.business.abstracts.check.GoogleMapsAPICheckService;
import com.gp.KuryeNet.core.business.concretes.check.GoogleMapsAPICheckManager;
import com.gp.KuryeNet.core.entities.ApiError;
import com.gp.KuryeNet.core.entities.DirectionsResponse;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorDataResult;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.dataAccess.abstracts.CourierDao;
import com.gp.KuryeNet.dataAccess.abstracts.CustomerDao;
import com.gp.KuryeNet.dataAccess.abstracts.OrderDao;
import com.gp.KuryeNet.entities.concretes.Courier;
import com.gp.KuryeNet.entities.concretes.Customer;
import com.gp.KuryeNet.entities.concretes.Order;

@Service
public class GoogleMapsAPIManager implements GoogleMapsAPIService{
	
	private CourierDao courierDao;
	private CustomerDao customerDao;
	private OrderDao orderDao;
	private GoogleMapsAPICheckService googleMapsAPICheckService;
	private RestTemplate restTemplate;
	
	@Autowired
	public GoogleMapsAPIManager(CourierDao courierDao, CustomerDao customerDao,RestTemplate restTemplate,OrderDao orderDao,GoogleMapsAPICheckService googleMapsAPICheckService) {
		super();
		this.courierDao = courierDao;
		this.customerDao = customerDao;
		this.orderDao = orderDao;
		this.restTemplate = restTemplate;
		this.googleMapsAPICheckService = googleMapsAPICheckService;
	}

	

	@Async
	@Transactional
	@Override
	public Result getRemainingDataFromGoogleMaps(String courierEmail, int orderId) {
		
		googleMapsAPICheckService.isCourierInDistribution(courierEmail);
		googleMapsAPICheckService.isOrderInDistribution(orderId);
		ErrorDataResult<ApiError> errors= Utils.getErrorsIfExist(googleMapsAPICheckService);
		if(errors!=null) return errors;
		
		DirectionsResponse directionsResponse = new DirectionsResponse();
		Courier courier = courierDao.getByCourierEmail(courierEmail);
		double courierlat = courier.getCourierLatitude();
		double courierlong = courier.getCourierLongitude();
		
		String orign = courierlat+","+courierlong;
		
		//System.out.println(destination);
		
		Order order = orderDao.getByOrderId(orderId);
		Customer customer = order.getCustomer();
		
		//Customer customer = customerDao.getByCustomerId(customerId);
		double customerlat = customer.getCustomerLatitude();
		double customerlong = customer.getCustomerLongitude();
		
		String destination = customerlat + "," + customerlong;
		
		//System.out.println(orign);
		
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        String url = "https://maps.googleapis.com/maps/api/directions/json";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("destination", destination)
                .queryParam("origin", orign)
                .queryParam("key", Utils.Const.GOOGLE_MAPS_API_KEY)
                .queryParam("mode", "driving")
                .queryParam("departure_time", "now")
                .queryParam("traffic_model", "best_guess");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, String.class);

        //System.out.println(response);

        int remainingMinutes = 0;
        String remainingMinutesText= null;
        int remainingDistance = 0;
        String remainingDistanceText = null;
        
        ObjectMapper objectMapper = new ObjectMapper();
        
        // Handle the response
        if (response.getStatusCode().is2xxSuccessful()) {
            String responseBody = response.getBody();
            
            try {
                
                JsonNode rootNode = objectMapper.readTree(responseBody);

                JsonNode routes = rootNode.get("routes");
                if (routes != null && routes.isArray() && routes.size() > 0) {
                    JsonNode legs = routes.get(0).get("legs");
                    if (legs != null && legs.isArray() && legs.size() > 0) {
                        JsonNode durationInTraffic = legs.get(0).get("duration_in_traffic");
                        JsonNode distance = legs.get(0).get("distance");
                        if (durationInTraffic != null && durationInTraffic.has("text") && durationInTraffic.has("value") && distance.has("text") && distance.has("value")) {
                            String remainingMinutesTextData = durationInTraffic.get("text").asText();
                            String remainingMinutesData = durationInTraffic.get("value").asText();
                            remainingMinutesTextData = remainingMinutesTextData.replace("hour", "saat").replace("hours", "saat").replace("mins", "dakika").replace("min", "dakika");
                            remainingMinutes = Integer.parseInt(remainingMinutesData)/60;
                            remainingMinutesText = remainingMinutesTextData;
                            
                            String remainingDistanceTextData = distance.get("text").asText();
                            String remainingDistanceData = distance.get("value").asText();
                            remainingDistance = Integer.parseInt(remainingDistanceData);
                            remainingDistanceText = remainingDistanceTextData;
                            
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
                        
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.MINUTE, remainingMinutes);
            Date estimatedDeliveryDate = calendar.getTime();
            
            order.setRemainingMinutes(remainingMinutes);
            order.setEstimatedDeliveryTime(estimatedDeliveryDate);
            orderDao.save(order);
            
            directionsResponse.setRemainingMinutes(remainingMinutes);
            directionsResponse.setRemainingMinutesText(remainingMinutesText);
            directionsResponse.setRemainingDistance(remainingDistance);
            directionsResponse.setRemainingDistanceText(remainingDistanceText);
                
        } 
        
        else {
        	return new ErrorDataResult<>("Request failed with status code: " + response.getStatusCode());
        }
        
        return new SuccessDataResult<DirectionsResponse>(directionsResponse,"Trafiğe Göre Tahmini Teslimat Dakikası Hesaplanmıştır.");
	}



	@Async
	@Transactional
	@Override
	public Result getDirectionsFromGoogleMaps(String courierEmail, int orderId) {
		googleMapsAPICheckService.isCourierInDistribution(courierEmail);
		googleMapsAPICheckService.isOrderInDistribution(orderId);
		ErrorDataResult<ApiError> errors= Utils.getErrorsIfExist(googleMapsAPICheckService);
		if(errors!=null) return errors;
		
		Courier courier = courierDao.getByCourierEmail(courierEmail);
		double courierlat = courier.getCourierLatitude();
		double courierlong = courier.getCourierLongitude();
		
		String orign = courierlat+","+courierlong;
		
		//System.out.println(destination);
		
		Order order = orderDao.getByOrderId(orderId);
		Customer customer = order.getCustomer();
		
		//Customer customer = customerDao.getByCustomerId(customerId);
		double customerlat = customer.getCustomerLatitude();
		double customerlong = customer.getCustomerLongitude();
		
		String destination = customerlat + "," + customerlong;
		
		//System.out.println(orign);
		
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();

        String url = "https://maps.googleapis.com/maps/api/directions/json";
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
                .queryParam("destination", destination)
                .queryParam("origin", orign)
                .queryParam("key", Utils.Const.GOOGLE_MAPS_API_KEY)
                .queryParam("mode", "driving")
                .queryParam("departure_time", "now")
                .queryParam("traffic_model", "best_guess")
                .queryParam("alternatives", "true");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<String> response = restTemplate.exchange(builder.toUriString(), HttpMethod.GET, requestEntity, String.class);
        String responseBody = response.getBody();
        //System.out.print(responseBody);
        
        return new SuccessDataResult<>(responseBody, "Yol Tarifi alınmıştır.");

	}

}
