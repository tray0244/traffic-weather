package com.example.traffic.weather.Service;

import com.example.traffic.weather.dto.WeatherResponseDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${openweathermap.api.key}")
    private String apiKey;

    public WeatherResponseDto getWeather(String lat, String lon) {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat
                + "&lon=" + lon + "&appid=" + apiKey + "&units=metric&lang=kr";

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject(apiUrl, WeatherResponseDto.class);
    }
}
