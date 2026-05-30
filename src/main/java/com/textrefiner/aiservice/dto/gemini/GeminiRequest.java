package com.textrefiner.aiservice.dto.gemini;

import lombok.Builder;
import lombok.Getter;
import java.util.List;

@Getter @Builder
public class GeminiRequest {
    private List<Content> contents;

    @Getter @Builder
    public static class Content {
        private List<Part> parts;
    }

    @Getter @Builder
    public static class Part {
        private String text; // 여기에 진짜 다듬을 텍스트가 들어감!
    }
}