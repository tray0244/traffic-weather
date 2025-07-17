package com.example.traffic.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter

public class WeatherResponseDto {
    private List<Weather> weather;
    private Main main;
    private String name;

    @Getter
    @Setter
    public static class Weather {
        private String description;
        private String icon;
    }

    @Getter
    @Setter
    public static class Main {
        private Double temp;
    }
}
