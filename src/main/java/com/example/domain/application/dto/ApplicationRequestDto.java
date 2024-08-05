package com.example.domain.application.dto;

import lombok.Getter;

@Getter
public class ApplicationRequestDto {

    private final Long userId;
    private final Long jobPostId;

    public ApplicationRequestDto(Long userId, Long jobPostId){
        this.userId = userId;
        this.jobPostId = jobPostId;
    }
}
