package com.gp.KuryeNet.core.business.concretes;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gp.KuryeNet.core.business.abstracts.AIModelService;
import com.gp.KuryeNet.core.business.abstracts.check.GoogleMapsAPICheckService;
import com.gp.KuryeNet.core.entities.AIModelPredictionRequest;
import com.gp.KuryeNet.core.entities.AIModelPredictionResponse;
import com.gp.KuryeNet.core.entities.WeatherResponse;
import com.gp.KuryeNet.core.utulities.result.Result;
import com.gp.KuryeNet.core.utulities.result.SuccessDataResult;
import com.gp.KuryeNet.dataAccess.abstracts.CourierDao;
import com.gp.KuryeNet.dataAccess.abstracts.CustomerDao;
import com.gp.KuryeNet.dataAccess.abstracts.OrderDao;
import com.gp.KuryeNet.entities.concretes.Courier;
import com.gp.KuryeNet.entities.concretes.Order;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import com.fasterxml.jackson.databind.ObjectMapper;



@Service
public class AIModelManager implements AIModelService{
	
	private CourierDao courierDao;
	private OrderDao orderDao;
	private GoogleMapsAPICheckService googleMapsAPICheckService;
	private RestTemplate restTemplate;
	private CustomerDao customerDao;
	
	@Autowired
	public AIModelManager(CourierDao courierDao, OrderDao orderDao, GoogleMapsAPICheckService googleMapsAPICheckService,
			RestTemplate restTemplate, CustomerDao customerDao) {
		super();
		this.courierDao = courierDao;
		this.orderDao = orderDao;
		this.googleMapsAPICheckService = googleMapsAPICheckService;
		this.restTemplate = restTemplate;
		this.customerDao = customerDao;
	}
	
	int Delivery_Person_Age = 0;
	int Multiple_Deliveries = 0;
	String City = null;
	String Type_Of_Order = null;
	String Type_Of_Vehicle = null;
	String Day_Type = null;
    String Time_Category = null;
    String Weather_Condition = null;
    int Distance = 0;
    String Road_Traffic_Density = null;
    
    double Prediction = 0.0;
    int PredictionRound = 0;

	

	@Async
	@Override
	public SuccessDataResult<AIModelPredictionResponse> getPrediction(String courierEmail, int orderId) {
		
		Order order = orderDao.getByOrderId(orderId);
		Courier courier = courierDao.getByCourierEmail(courierEmail);
		
		String orderType = order.getOrderType();
		String orderDate = order.getOrderDate().toString();
		String orderCity = order.getOrderAddress().getCity();
		
		String vehicleType = courier.getVehicle().getVehicleType();
		
		//Delivery_Person_Age
		Delivery_Person_Age = Calendar.getInstance().get(Calendar.YEAR) - Integer.parseInt(courier.getCourierBirthday().toString().split("-")[0]);

		//Multiple_Deliveries
		Multiple_Deliveries = 0;
		
		//City
		final List<String> metropolitanCities = Arrays.asList(
		            "adana", "ankara", "antalya", "aydın", "balıkesir", "bursa",
		            "denizli", "diyarbakır", "erzurum", "eskişehir", "gaziantep",
		            "hatay", "istanbul", "izmir", "kahramanmaraş", "kayseri",
		            "kocaeli", "konya", "malatya", "manisa", "mardin",
		            "mersin", "muğla", "ordu", "sakarya", "samsun", "şanlıurfa",
		            "tekirdağ", "trabzon", "van");

        if (metropolitanCities.contains(orderCity.toLowerCase())) {
        	City = "Metropolitan";
        } else {
        	City = "Urban";
        }
        
		        
		
		//Type_Of_Order
		if (orderType.toLowerCase().equals("yemek")) {
			Type_Of_Order = "Meal";
		}
		else if (orderType.toLowerCase().equals("market")) {
			Type_Of_Order = "Market";
		}
		else if (orderType.toLowerCase().equals("içecek")) {
			Type_Of_Order = "Drinks";
		}
		else if (orderType.toLowerCase().equals("atıştırmalık")) {
			Type_Of_Order = "Snack";
		}
		
		//Type_Of_Vehicle
		


		if (vehicleType.toLowerCase().equals("motor")) {
			Type_Of_Vehicle = "Motorcycle";
		}
		else if (vehicleType.toLowerCase().equals("araba")) {
			Type_Of_Vehicle = "Car";
		}
		else if (vehicleType.toLowerCase().equals("skoter")) {
			Type_Of_Vehicle = "Scooter";
		}
		else if (vehicleType.toLowerCase().equals("elektrikli skoter")) {
			Type_Of_Vehicle = "ElectricScooter";
		}
		
		
		//Day_Type

	    final List<String> holidays = Arrays.asList("01-01-2024", "23-04-2024", "01-05-2024", "09-04-2024", "10-04-2024","11-04-2024","12-04-2024","19-05-2024", "15-06-2024","16-06-2024","17-06-2024","18-06-2024","19-06-2024","15-07-2024", "30-08-2024", "29-10-2024","31-12-2024");
	    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
	    // Gelen tarih formatını DateTimeFormatter ile tanımla
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Çıkış formatı
        // Gelen tarihi parse et
        LocalDateTime dateTime = LocalDateTime.parse(orderDate, inputFormatter);
        // Tarihi istenen formata çevir
        String formattedDateTime = dateTime.format(outputFormatter);
        // Tatil kontrolü
        // Tatilleri de yeni formatınıza göre güncelleyin
        if (holidays.contains(formattedDateTime)) {
            Day_Type = "Holiday";
        } else {
            // Hafta sonu kontrolü
            switch (dateTime.getDayOfWeek()) {
                case SATURDAY:
                case SUNDAY:
                    Day_Type = "Weekend";
                    break;
                default:
                    Day_Type = "Weekday";
            }
        }

        //Time_Category

        int hour = dateTime.getHour();
        
        if (6 <= hour && hour < 12) {
        	Time_Category = "Morning";
        } else if (12 <= hour && hour < 17) {
        	Time_Category = "Noon";
        } else if (17 <= hour && hour < 21) {
        	Time_Category = "Evening";
        } else {
        	Time_Category = "Night";
        }
        
        //Weather_Condition

        
        OpenWeatherMapManager openWeatherMapManager = new OpenWeatherMapManager(courierDao);
        SuccessDataResult<WeatherResponse> result = (SuccessDataResult<WeatherResponse>) openWeatherMapManager.getWeather(courierEmail);
        WeatherResponse weatherResponse = result.getData();
        Weather_Condition = weatherResponse.getWeatherCondition();
        
        if(weatherResponse.getWeatherCondition().toLowerCase() == "clouds") {
        	Weather_Condition = "Cloudy";
        }
        
        
        //Distance
        //Road_Traffic_Density
        
        GoogleMapsAPIManager googleMapsAPIManager = new GoogleMapsAPIManager(courierDao,customerDao,restTemplate,orderDao,googleMapsAPICheckService);
        SuccessDataResult<String> result2 = (SuccessDataResult<String>) googleMapsAPIManager.getDirectionsFromGoogleMaps(courierEmail, orderId);
        try {
			JSONObject jsonObject= new JSONObject(result2.getData());
	        JSONArray routes = jsonObject.getJSONArray("routes");
	        JSONObject route = routes.getJSONObject(0);
	        JSONArray legs = route.getJSONArray("legs");
	        JSONObject leg = legs.getJSONObject(0);
	        JSONObject distance = leg.getJSONObject("distance");
	        JSONObject duration = leg.getJSONObject("duration");
	        JSONObject durationInTraffic = leg.getJSONObject("duration_in_traffic");
	        int distanceValue = distance.getInt("value");
	        int durationValue = duration.getInt("value");
	        int durationInTrafficValue = durationInTraffic.getInt("value");
	        
	        Distance = distanceValue;
	        

	        
	        // Yüzdesel farkı hesapla
	        double percentageDifference = Math.abs(((double)(durationInTrafficValue - durationValue) / durationValue)) * 100;

	        // Koşullara göre kategorize et
	        if (0 <= percentageDifference && percentageDifference <= 20) {
	        	Road_Traffic_Density = "Low";
	        } else if (20 < percentageDifference && percentageDifference <= 40) {
	        	Road_Traffic_Density = "Medium";
	        } else if (40 < percentageDifference && percentageDifference <= 60) {
	        	Road_Traffic_Density =  "High";
	        } else {
	        	Road_Traffic_Density =  "Jam";
	        }
	        
	        
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        AIModelPredictionRequest aiModelPredictionRequest = new AIModelPredictionRequest();
        aiModelPredictionRequest.setCity(City);
        aiModelPredictionRequest.setDay_Type(Day_Type);
        aiModelPredictionRequest.setDelivery_Person_Age(Delivery_Person_Age);
        aiModelPredictionRequest.setDistance(Distance);
        aiModelPredictionRequest.setMultiple_Deliveries(Multiple_Deliveries);
        aiModelPredictionRequest.setRoad_Traffic_Density(Road_Traffic_Density);
        aiModelPredictionRequest.setTime_Category(Time_Category);
        aiModelPredictionRequest.setType_Of_Order(Type_Of_Order);
        aiModelPredictionRequest.setType_Of_Vehicle(Type_Of_Vehicle);
        aiModelPredictionRequest.setWeather_Condition(Weather_Condition);
        
        
        try {
            RestTemplate restTemplate = new RestTemplate();
            ObjectMapper objectMapper = new ObjectMapper();
            String apiUrl = "https://kuryenetmlflask-b86f952ff9a1.herokuapp.com/predict"; // API'nizin URL'si

            // AIModelPredictionRequest nesnesini JSON string'ine dönüştür
            String jsonRequest = objectMapper.writeValueAsString(aiModelPredictionRequest);

            // HTTP Header ayarları
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBasicAuth("YWRtaW46YWRtaW4=");

            // HttpEntity oluştur
            HttpEntity<String> entity = new HttpEntity<>(jsonRequest, headers);

            // POST isteğini gönder ve yanıtı al
            ResponseEntity<String> responsePrediction = restTemplate.postForEntity(apiUrl, entity, String.class);
            
            JSONObject jsonObject= new JSONObject(responsePrediction.getBody());
            Prediction = jsonObject.getDouble("Prediction");
            PredictionRound = jsonObject.getInt("PredictionRound");
                 
        
        } catch (Exception e) {
            e.printStackTrace();
        }

		
		AIModelPredictionResponse aiModelPredictionResponse = new AIModelPredictionResponse();
		aiModelPredictionResponse.setPrediction(Prediction);
		aiModelPredictionResponse.setPredictionRound(PredictionRound);
		order.setDeliveryMinutesAI(PredictionRound);
		orderDao.save(order);
		return new SuccessDataResult<AIModelPredictionResponse>(aiModelPredictionResponse,"successful");
	}
		

	@Async
	@Override
	public Result writeData(String courierEmail, int orderId) {
		// TODO Auto-generated method stub
		return null;
	}

}
