package com.assignment1.clothes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.client.ClientHttpRequestInterceptor;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Configuration
public class AppConfig {

    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin123";

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();

        // Add the Basic Authentication header to the request
        List<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
        interceptors.add((request, body, execution) -> {
            String auth = USERNAME + ":" + PASSWORD;
            String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Basic " + encodedAuth);
            request.getHeaders().addAll(headers);
            return execution.execute(request, body);
        });

        restTemplate.setInterceptors(interceptors);
        return restTemplate;
    }
}
