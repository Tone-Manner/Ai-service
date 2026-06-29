package com.textrefiner.aiservice.controller;

import com.textrefiner.aiservice.dto.RefineRequest;
import com.textrefiner.aiservice.dto.RefineResponse;
import com.textrefiner.aiservice.service.AiTextService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiTextController {

    private final AiTextService aiTextService;

    @PostMapping("/refine")
    public ResponseEntity<RefineResponse> refineText(@RequestBody RefineRequest request) {
        // List<String> 형태로 5개의 문장을 받아옴
        List<String> refinedResults = aiTextService.refineText(request.getText(), request.getRelation());

        // 응답 상자에 리스트를 담아서 반환
        return ResponseEntity.ok(new RefineResponse(refinedResults));
    }
}