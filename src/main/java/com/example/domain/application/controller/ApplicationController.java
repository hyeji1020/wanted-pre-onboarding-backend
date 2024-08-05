package com.example.domain.application.controller;

import com.example.domain.application.dto.ApplicationRequestDto;
import com.example.domain.application.model.Application;
import com.example.domain.application.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/applications")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @PostMapping
    public ResponseEntity<Application> createApplication(@RequestBody ApplicationRequestDto requestDto) {

        // 서비스 계층을 통해 지원서 생성
        Application application = applicationService.createApplication(requestDto);

        return ResponseEntity.ok(application);
    }
}
