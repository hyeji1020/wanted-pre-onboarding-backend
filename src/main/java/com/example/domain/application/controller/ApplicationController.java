package com.example.domain.application.controller;

import com.example.domain.application.dto.ApplicationRequestDto;
import com.example.domain.application.dto.ApplicationResponseDto;
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
    public ResponseEntity<ApplicationResponseDto> createApplication(@RequestBody ApplicationRequestDto requestDto) {
        ApplicationResponseDto application = applicationService.createApplication(requestDto);
        return ResponseEntity.ok(application);
    }
}
