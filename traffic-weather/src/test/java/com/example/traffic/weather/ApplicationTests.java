package com.example.traffic.weather;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Test
	@DisplayName("/api/test 엔드포인트가 정상적으로 응답하는지 테스트")
	void testHelloWorldEndpoint() throws Exception {

		mockMvc.perform(get("/api/test"))
				.andExpect(status().isOk())
				.andExpect(content().string("안녕하세요! 백엔드 서버에서 보낸 메시지입니다!")) // 3. 응답 본문 내용이 예상과 같은지 검증한다.
				.andDo(print());
	}
}