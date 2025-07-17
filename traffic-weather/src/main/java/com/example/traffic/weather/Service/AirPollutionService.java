package com.example.traffic.weather.Service;

import com.example.traffic.weather.dto.AirPollutionDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AirPollutionService { // "Service"로 수정

    @Value("${openweathermap.api.key}")
    private String apiKey;

    public AirPollutionDto getAirPollution(String lat, String lon) {
        String apiUrl = "http://api.openweathermap.org/data/2.5/air_pollution?lat=" + lat
                + "&lon=" + lon + "&appid=" + apiKey;

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(apiUrl, AirPollutionDto.class);
    }
}