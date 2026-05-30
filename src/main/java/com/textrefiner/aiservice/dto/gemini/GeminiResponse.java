package com.textrefiner.aiservice.dto.gemini;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class GeminiResponse {
    private List<Candidate> candidates;

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Candidate {
        private Content content;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content {
        private List<Part> parts;
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Part {
        private String text;
    }

    // 복잡한 JSON 껍데기를 까고 순수 알맹이 텍스트만 쏙 빼오는 메서드
    public String getExtractedText() {
        if (candidates != null && !candidates.isEmpty() && candidates.get(0).getContent() != null) {
            return candidates.get(0).getContent().getParts().get(0).getText();
        }
        return "AI 응답을 불러오지 못했습니다.";
    }
}