package com.textrefiner.aiservice.client;

import com.textrefiner.aiservice.dto.gemini.GeminiRequest;
import com.textrefiner.aiservice.dto.gemini.GeminiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

// url 속성에 application.yml 에 적어둔 주소를 매핑해 줌
@FeignClient(name = "geminiClient", url = "${gemini.url}")
public interface GeminiClient {

    @PostMapping
    GeminiResponse generateContent(
            @RequestParam("key") String apiKey, // URL 파라미터로 ?key=API_KEY 가 붙음
            @RequestBody GeminiRequest request  // Body에 JSON 데이터가 담김
    );
}