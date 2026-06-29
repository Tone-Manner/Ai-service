package com.textrefiner.aiservice.service;

import com.textrefiner.aiservice.client.GeminiClient;
import com.textrefiner.aiservice.dto.gemini.GeminiRequest;
import com.textrefiner.aiservice.dto.gemini.GeminiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AiTextService {

    private final GeminiClient geminiClient;

    @Value("${gemini.api-key}")
    private String apiKey;

    public List<String> refineText(String rawText, String relation) {

        // [프롬프트 엔지니어링 업그레이드] 5가지 버전을 요구
        String prompt = "너는 친절하고 전문적인 텍스트 교정기야. " +
                "사용자가 입력한 문장을 [" + relation + "]에게 보내는 상황에 맞게 가장 자연스럽고 적절한 톤앤매너로 다듬어서 딱 5가지 버전을 제안해줘. " +
                "각 버전은 약간씩 다른 뉘앙스를 가지면 좋아. " +
                "주의사항: 인사말이나 부가적인 설명은 절대 하지 말고, 각 문장을 줄바꿈(엔터)으로만 구분해서 출력해. " +
                "문장 앞에 번호나 기호도 붙이지 마.\n\n" +
                "원본 문장: " + rawText;

        GeminiRequest.Part part = GeminiRequest.Part.builder().text(prompt).build();
        GeminiRequest.Content content = GeminiRequest.Content.builder().parts(List.of(part)).build();
        GeminiRequest request = GeminiRequest.builder().contents(List.of(content)).build();

        GeminiResponse response = geminiClient.generateContent(apiKey, request);
        String rawResponse = response.getExtractedText();

        return Arrays.stream(rawResponse.split("\\r?\\n"))
                .map(String::trim) // 앞뒤 공백 제거
                .filter(text -> !text.isEmpty()) // 혹시 모를 빈 줄(엔터 두 번 쳐진 곳) 제거
                .collect(Collectors.toList());
    }
}