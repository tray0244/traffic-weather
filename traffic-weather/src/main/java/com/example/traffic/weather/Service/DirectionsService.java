package com.example.traffic.weather.Service;

import com.example.traffic.weather.dto.KakaoDirectionsResponseDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

// 이 클래스는 길찾기 관련 비즈니스 로직을 처리하는 서비스입니다.
@Service
public class DirectionsService {

    // 로그 출력을 위해 Logger 객체를 생성합니다.
    private static final Logger logger = LoggerFactory.getLogger(DirectionsService.class);

    // application.properties에서 카카오 REST API 키를 주입받습니다.
    @Value("${kakao.api.rest.key}")
    private String kakaoRestApiKey;

    /**
     * 출발지와 목적지 좌표로 자동차 길찾기 경로를 요청하는 메소드
     * @param origin      출발지 좌표 "경도,위도" 형식
     * @param destination 목적지 좌표 "경도,위도" 형식
     * @return 카카오 API의 길찾기 응답 데이터
     */
    public KakaoDirectionsResponseDto getDirections(String origin, String destination) {

        // ★★★ 디버깅을 위해, API 호출 직전에 실제 사용되는 키 값을 로그로 출력합니다. ★★★
        logger.info("카카오 길찾기 API 호출, 사용하는 REST API 키: [{}]", kakaoRestApiKey);

        // 카카오모빌리티 길찾기 API의 공식 주소입니다.
        String apiUrl = "https://apis-navi.kakaomobility.com/v1/directions";

        // 요청 파라미터를 포함한 URL을 안전하게 생성합니다.
        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(apiUrl)
                .queryParam("origin", origin)
                .queryParam("destination", destination);

        // API 요청을 보낼 객체를 생성합니다.
        RestTemplate restTemplate = new RestTemplate();
        // HTTP 헤더에 인증 정보를 설정합니다.
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK " + kakaoRestApiKey);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // 설정된 정보를 바탕으로 카카오 서버에 GET 요청을 보내고, 응답을 DTO 객체로 받습니다.
        return restTemplate.exchange(
                builder.toUriString(),
                HttpMethod.GET,
                entity,
                KakaoDirectionsResponseDto.class
        ).getBody();
    }
}