package com.gp.KuryeNet.core.clients;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class ExternalApiClient {

    private final RestTemplate restTemplate;

    public ExternalApiClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Retry(name = "googleMaps")
    @CircuitBreaker(name = "googleMaps", fallbackMethod = "fallbackResponse")
    public ResponseEntity<String> getGoogleMaps(String url, HttpEntity<String> requestEntity) {
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
    }

    @Retry(name = "openWeather")
    @CircuitBreaker(name = "openWeather", fallbackMethod = "fallbackResponse")
    public ResponseEntity<String> getOpenWeather(String url, HttpEntity<String> requestEntity) {
        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
    }

    @Retry(name = "aiModel")
    @CircuitBreaker(name = "aiModel", fallbackMethod = "fallbackResponse")
    public ResponseEntity<String> postAiModel(String url, HttpEntity<String> requestEntity) {
        return restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
    }

    private ResponseEntity<String> fallbackResponse(String url, HttpEntity<String> requestEntity, Throwable throwable) {
        return ResponseEntity.status(503).body("External service unavailable");
    }
}
