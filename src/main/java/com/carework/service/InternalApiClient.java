package com.carework.service;

import com.carework.config.CareworkProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class InternalApiClient {

    private final RestTemplate restTemplate;
    private final CareworkProperties properties;

    public <T> T get(String path, ParameterizedTypeReference<T> responseType) {
        ResponseEntity<T> response = restTemplate.exchange(
                properties.getWeb().getApiBaseUrl() + path,
                HttpMethod.GET,
                new HttpEntity<>(buildHeaders()),
                responseType
        );
        return response.getBody();
    }

    public <T> T get(String path, Class<T> responseType) {
        ResponseEntity<T> response = restTemplate.exchange(
                properties.getWeb().getApiBaseUrl() + path,
                HttpMethod.GET,
                new HttpEntity<>(buildHeaders()),
                responseType
        );
        return response.getBody();
    }

    public <T, R> R post(String path, T body, Class<R> responseType) {
        ResponseEntity<R> response = restTemplate.exchange(
                properties.getWeb().getApiBaseUrl() + path,
                HttpMethod.POST,
                new HttpEntity<>(body, buildHeaders()),
                responseType
        );
        return response.getBody();
    }

    private HttpHeaders buildHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-API-KEY", properties.getSecurity().getApiKey());
        return headers;
    }
}
