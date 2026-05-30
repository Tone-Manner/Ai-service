package com.textrefiner.aiservice.controller;

import com.textrefiner.aiservice.client.GeminiClient;
import com.textrefiner.aiservice.dto.gemini.GeminiRequest;
import com.textrefiner.aiservice.dto.gemini.GeminiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ai")
public class AiTestController {

    private final GeminiClient geminiClient;
    private final String apiKey;

    // application.yml 에 있는 키를 가져옴
    public AiTestController(GeminiClient geminiClient, @Value("${gemini.api-key}") String apiKey) {
        this.geminiClient = geminiClient;
        this.apiKey = apiKey;
    }

    @GetMapping("/test-gemini")
    public ResponseEntity<String> testGemini() {
        // 1. Gemini에게 보낼 질문 조립하기
        GeminiRequest.Part part = GeminiRequest.Part.builder().text("안녕? 넌 누구야? 한글로 짧게 대답해줘.").build();
        GeminiRequest.Content content = GeminiRequest.Content.builder().parts(List.of(part)).build();
        GeminiRequest request = GeminiRequest.builder().contents(List.of(content)).build();

        // 2. 구글(Gemini) 서버로 전화 걸기
        GeminiResponse response = geminiClient.generateContent(apiKey, request);

        // 3. 응답에서 알맹이만 빼서 반환
        return ResponseEntity.ok("Gemini의 대답: " + response.getExtractedText());
    }
}