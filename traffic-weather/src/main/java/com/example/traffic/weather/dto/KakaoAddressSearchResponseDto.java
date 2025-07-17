package com.example.traffic.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

// API 응답에서 우리가 사용하지 않는 필드들은 무시하도록 설정합니다.
@JsonIgnoreProperties(ignoreUnknown = true)
public class KakaoAddressSearchResponseDto {

    // 응답 결과의 'documents'라는 이름의 JSON 배열을 담을 리스트입니다.
    @JsonProperty("documents")
    private List<Document> documents;

    public List<Document> getDocuments() {
        return documents;
    }

    // 'documents' 배열 안의 각 주소 정보를 담을 내부 클래스입니다.
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Document {
        // 주소의 경도(Longitude) 값을 담을 필드입니다.
        @JsonProperty("x")
        private String x;

        // 주소의 위도(Latitude) 값을 담을 필드입니다.
        @JsonProperty("y")
        private String y;

        public String getX() {
            return x;
        }

        public String getY() {
            return y;
        }
    }
}