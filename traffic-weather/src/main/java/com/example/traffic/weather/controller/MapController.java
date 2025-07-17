package com.example.traffic.weather.controller;

import com.example.traffic.weather.Service.DirectionsService;
import com.example.traffic.weather.Service.KakaoAddressSearchService;
import com.example.traffic.weather.dto.KakaoAddressSearchResponseDto;
import com.example.traffic.weather.dto.KakaoDirectionsResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 이 클래스는 지도와 관련된 API 요청을 처리하는 컨트롤러입니다.
 * (예: 주소 검색, 경로 검색 등)
 */
@RestController
public class MapController {

    // final 키워드는 이 서비스가 반드시 필요하며, 한번 주입되면 바뀌지 않음을 의미합니다.
    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionsService directionsService; // DirectionsService 주입
    /**
     * 생성자(Constructor)를 통해 필요한 서비스(KakaoAddressSearchService)를 주입(Dependency Injection)받습니다.
     * 스프링이 시작될 때 이 생성자를 자동으로 호출하여 서비스 객체를 넣어줍니다.
     * @param kakaoAddressSearchService 주소 검색 로직을 처리하는 서비스
     */
    public MapController(KakaoAddressSearchService kakaoAddressSearchService) {
        this.kakaoAddressSearchService = kakaoAddressSearchService;
        this.directionsService = new DirectionsService();
    }

    /**
     * '/api/search' 경로로 GET 요청이 오면 이 메소드가 실행됩니다.
     * @param query 사용자가 검색한 주소 문자열 (예: "강남역")
     * @return 검색된 주소의 좌표 정보(Document 객체)
     */
    @GetMapping("/api/search")
    public KakaoAddressSearchResponseDto.Document searchAddress(@RequestParam String query) {
        // KakaoAddressSearchService를 호출하여 주소를 검색하고 그 결과를 클라이언트에게 반환합니다.
        return kakaoAddressSearchService.searchAddress(query);
    }

    @GetMapping("/api/directions")
    public KakaoDirectionsResponseDto getDirections(@RequestParam String origin, @RequestParam String destination) {
        //DirectionsService를 호출하여 길찾기 결과를 요청하고 반환합니다.
        return directionsService.getDirections(origin, destination);
    }
}