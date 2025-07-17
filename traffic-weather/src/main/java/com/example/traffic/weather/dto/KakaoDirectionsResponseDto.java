package com.example.traffic.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * 카카오 길찾기 API의 응답을 담기 위한 DTO 클래스입니다.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoDirectionsResponseDto {

    // 여러 경로 결과 중 첫 번째 경로만 사용합니다.
    @JsonProperty("routes")
    private List<Route> routes;

    public List<Route> getRoutes() { return routes; }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Route {
        // 경로에 대한 요약 정보를 담습니다.
        @JsonProperty("summary")
        private Summary summary;

        // 경로를 구성하는 도로(road)들의 목록입니다.
        @JsonProperty("sections")
        private List<Section> sections;

        public Summary getSummary() { return summary; }
        public List<Section> getSections() { return sections; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Summary {
        // 경로의 총 소요 시간 (초 단위)
        @JsonProperty("duration")
        private int duration;

        public int getDuration() { return duration; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Section {
        // 경로를 그리기 위한 좌표열(vertexes)을 담고 있는 도로 목록입니다.
        @JsonProperty("roads")
        private List<Road> roads;

        public List<Road> getRoads() { return roads; }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Road {
        // 도로의 꼭지점 좌표 목록 (경도, 위도, 경도, 위도...)
        @JsonProperty("vertexes")
        private List<Double> vertexes;

        public List<Double> getVertexes() { return vertexes; }
    }
}