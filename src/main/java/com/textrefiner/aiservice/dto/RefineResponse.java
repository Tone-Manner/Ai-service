package com.textrefiner.aiservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import java.util.List;

@Getter
@AllArgsConstructor
public class RefineResponse {
    private List<String> refinedTexts; // AI가 예쁘게 다듬은 결과물
}
