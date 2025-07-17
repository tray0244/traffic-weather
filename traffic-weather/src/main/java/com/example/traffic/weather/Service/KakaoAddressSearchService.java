package com.example.traffic.weather.Service;

import com.example.traffic.weather.dto.KakaoAddressSearchResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class KakaoAddressSearchService {

    private static final Logger logger = LoggerFactory.getLogger(KakaoAddressSearchService.class);

    // @Value("${kakao.api.rest.key}")
    // private String kakaoRestApiKey;

    public KakaoAddressSearchResponseDto.Document searchAddress(String query) {

        String kakaoRestApiKey = "eee38731657461f35af34b5f14a09607";
        String apiUrl = "https://dapi.kakao.com/v2/local/search/keyword.json";

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("query", query);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> responseEntity = restTemplate.exchange(
                // ★★★ 이 부분을 수정하여 한글을 올바르게 인코딩합니다. ★★★
                builder.build().encode().toUri(),
                HttpMethod.GET,
                entity,
                String.class
        );

        logger.info("카카오 주소 검색 API 응답: {}", responseEntity.getBody());

        ObjectMapper objectMapper = new ObjectMapper();
        KakaoAddressSearchResponseDto responseDto = null;
        try {
            responseDto = objectMapper.readValue(responseEntity.getBody(), KakaoAddressSearchResponseDto.class);
        } catch (JsonProcessingException e) {
            logger.error("Kakao API 응답 JSON 파싱 중 오류 발생", e);
        }

        if (responseDto != null && !responseDto.getDocuments().isEmpty()) {
            return responseDto.getDocuments().get(0);
        }

        return null;
    }
}