package com.textrefiner.aiservice.dto.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
    private int status;      // 상태 코드 (예: 500, 503)
    private String message;  // 에러 설명
}