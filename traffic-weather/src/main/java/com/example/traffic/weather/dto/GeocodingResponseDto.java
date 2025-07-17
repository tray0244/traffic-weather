package com.example.traffic.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitConnectionDetails;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeocodingResponseDto {

    private List<Address> addresses;

    public List<Address> getAddresses() { return addresses; }
    public void setAddresses(List<Address> addresses) { this.addresses = addresses; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Address {
        private String lon; //경도
        private String lat; //위도

        public String getX() { return lat; }
        public void setX(String lat) { this.lat = lat; }
        public String getY() { return lon; }
        public void setY(String lon) { this.lon = lon; }
    }
}
