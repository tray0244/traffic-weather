package com.example.traffic.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class AirPollutionDto {

    private List<PollutionData> list;

    public List<PollutionData> getList() { return list; }
    public void setList(List<PollutionData> list) { this.list = list; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class PollutionData {
        private Main main;
        private Components components;

        public Main getMain() { return main; }
        public void setMain(Main main) { this.main = main; }
        public Components getComponents() { return components; }
        public void setComponents(Components components) { this.components = components; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Main {
        // aqi: Air Quality Index. 1 = 좋음, 5 = 매우 나쁨
        private int aqi;
        public int getAqi() { return aqi; }
        public void setAqi(int aqi) { this.aqi = aqi; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Components {
        // 필드 이름에 '_'가 있으므로 @JsonProperty를 사용해 매핑합니다.
        @JsonProperty("pm2_5")
        private double pm25; // 초미세먼지

        private double pm10; // 미세먼지

        public double getPm25() { return pm25; }
        public void setPm25(double pm25) { this.pm25 = pm25; }
        public double getPm10() { return pm10; }
        public void setPm10(double pm10) { this.pm10 = pm10; }
    }
}