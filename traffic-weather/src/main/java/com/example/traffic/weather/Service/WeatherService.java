package com.example.traffic.weather.Service;

import com.example.traffic.weather.dto.WeatherResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WeatherService {
    @Value("${openweathermap.api.key}")
    private String apiKey;
    private static final Logger logger = LoggerFactory.getLogger(WeatherService.class);

    public WeatherResponseDto getWeather(String lat, String lon) {
        String apiUrl = "https://api.openweathermap.org/data/2.5/weather?lat=" + lat
                + "&lon=" + lon + "&appid=" + apiKey + "&units=metric&lang=kr";
        
        RestTemplate restTemplate = new RestTemplate();
        String jsonResponse = restTemplate.getForObject(apiUrl, String.class);
        logger.info("OpenWeatherMap API 응답: {}", jsonResponse);

        ObjectMapper objectMapper = new ObjectMapper();
        WeatherResponseDto weatherResponseDto = null;
        try {
            weatherResponseDto = objectMapper.readValue(jsonResponse, WeatherResponseDto.class);
        } catch (JsonProcessingException e) {
            logger.error("JSON 파싱 중 오류 발생", e);
        }

        return weatherResponseDto;
    }
}
