package com.gp.KuryeNet.core.business.concretes;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gp.KuryeNet.core.business.abstracts.GoogleMapsAPIService;
import com.gp.KuryeNet.core.entities.DirectionsResponse;
import com.gp.KuryeNet.core.utulities.Util.Utils;
import com.gp.KuryeNet.core.utulities.result.DataResult;
import com.gp.KuryeNet.core.utulities.result.ErrorDataResult;
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
	private RestTemplate restTemplate;
	
	@Autowired
	public GoogleMapsAPIManager(CourierDao courierDao, CustomerDao customerDao,RestTemplate restTemplate,OrderDao orderDao) {
		super();
		this.courierDao = courierDao;
		this.customerDao = customerDao;
		this.orderDao = orderDao;
		this.restTemplate = restTemplate;
	}

	

	@Override
	public DataResult<Integer> getDirectionsFromGoogleMaps(String courierEmail, int orderId) {
		
		Courier courier = courierDao.getByCourierEmail(courierEmail);
		double courierlat = courier.getCourierLatitude();
		double courierlong = courier.getCourierLongitude();
		
		String destination = courierlat+","+courierlong;
		
		System.out.println(destination);
		
		Order order = orderDao.getByOrderId(orderId);
		Customer customer = order.getCustomer();
		
		//Customer customer = customerDao.getByCustomerId(customerId);
		double customerlat = customer.getCustomerLatitude();
		double customerlong = customer.getCustomerLongitude();
		
		String orign = customerlat + "," + customerlong;
		
		System.out.println(orign);
		
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

        System.out.println(response);

        int remainingMinutes = 0;
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
                        if (durationInTraffic != null && durationInTraffic.has("text")) {
                            // Now you can safely access the "text" node
                            String remainingMinutesText = durationInTraffic.get("text").asText().split(" ")[0];
                            remainingMinutes = Integer.parseInt(remainingMinutesText);
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            
            //order = customer.getOrders().get(0);
            order.setRemainingMinutes(remainingMinutes);
            orderDao.save(order);
                
        } 
        
        else {
        	return new ErrorDataResult<>("Request failed with status code: " + response.getStatusCode());
        }
        
        return new SuccessDataResult<Integer>(remainingMinutes);
	}

}
