package com.example.traffic.weather;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @GetMapping("/api/test")
    public String hello() {
        return "안녕하세요! 백엔드 서버에서 보낸 메시지입니다!";
    }
}