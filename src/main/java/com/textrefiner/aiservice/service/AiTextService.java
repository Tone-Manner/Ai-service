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

        // 빈 값이거나 너무 짧은 문장 차단
        if (rawText == null || rawText.trim().length() < 2) {
            throw new IllegalArgumentException("다듬을 문장이 너무 짧습니다. 2글자 이상 입력해주세요.");
        }

        // [프롬프트 엔지니어링 업그레이드] 5가지 버전을 요구
        String prompt = "너는 텍스트 톤앤매너 교정 전문가야.\n" +
                "사용자가 입력한 [원본 문장]을 [" + relation + "]에게 보내는 상황에 맞춰 다듬어줘.\n" +
                "반드시 아래 5가지 컨셉으로 각각 1개씩, 총 5개의 문장을 제안해.\n\n" +
                "1. 정중하고 격식 있는 톤\n" +
                "2. 부드럽고 친근한 톤\n" +
                "3. 핵심만 짚는 간결한 톤\n" +
                "4. 조심스럽고 우회적인 톤\n" +
                "5. 약간의 위트나 센스를 더한 톤\n\n" +
                "[출력 필수 규칙 - 반드시 지킬 것]\n" +
                "- 인사말, 부가 설명, '네, 다듬어보았습니다' 같은 사족 절대 금지\n" +
                "- 문장 앞에 번호(1., 2.), 기호(-, *), 컨셉 이름 등 어떠한 문자도 절대 붙이지 말것\n" +
                "- 5개의 문장은 오직 줄바꿈(엔터)으로만 구분해서 순수 텍스트만 출력할 것\n\n" +
                "[원본 문장]: " + rawText;

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