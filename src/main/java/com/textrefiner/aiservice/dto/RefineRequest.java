package com.textrefiner.aiservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RefineRequest {
    private String text; // 다듬고 싶은 원본 텍스트
    private String relation; // 관계
}