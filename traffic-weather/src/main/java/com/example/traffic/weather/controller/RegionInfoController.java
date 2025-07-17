package com.example.traffic.weather.controller;

import com.example.traffic.weather.Service.AirPollutionService;
import com.example.traffic.weather.Service.WeatherService;
import com.example.traffic.weather.dto.AirPollutionDto;
import com.example.traffic.weather.dto.WeatherResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class RegionInfoController {

    private final WeatherService weatherService;
    private final AirPollutionService airPollutionService;

    public RegionInfoController(WeatherService weatherService, AirPollutionService airPollutionService) {
        this.weatherService = weatherService;
        this.airPollutionService = airPollutionService;
    }

    // 불필요한 sidoName 파라미터를 제거합니다.
    @GetMapping("/api/region-info")
    public Map<String, Object> getRegionInfo(@RequestParam String lat, @RequestParam String lon) {
        WeatherResponseDto weather = weatherService.getWeather(lat, lon);
        // airQualityService를 호출할 때도 lat, lon을 넘겨줍니다.
        AirPollutionDto airPollution = airPollutionService.getAirPollution(lat, lon);

        return Map.of("weather", weather, "airPollution", airPollution);
    }
}
