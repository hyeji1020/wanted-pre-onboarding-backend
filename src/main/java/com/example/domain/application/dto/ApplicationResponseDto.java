package com.example.domain.application.dto;

import com.example.domain.application.model.Application;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ApplicationResponseDto {

    private final Long id;
    private final Long userId;
    private final String userName;
    private final Long jobPostId;
    private final String jobPosition;
    private final LocalDate applicationDate;

    @Builder
    public ApplicationResponseDto(Long id, Long userId, String userName, Long jobPostId, String jobPosition, LocalDate applicationDate) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.jobPostId = jobPostId;
        this.jobPosition = jobPosition;
        this.applicationDate = applicationDate;
    }

    public static ApplicationResponseDto createFromEntity(Application application) {
        return ApplicationResponseDto.builder()
                .id(application.getId())
                .userId(application.getUser().getId())
                .userName(application.getUser().getUsername())
                .jobPostId(application.getJobPost().getId())
                .jobPosition(application.getJobPost().getJobPosition())
                .applicationDate(application.getApplicationDate())
                .build();
    }
}
