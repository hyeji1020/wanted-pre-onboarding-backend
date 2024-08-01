package com.example.JobPost.dto;

import com.example.JobPost.model.JobPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class JobPostResponseDto {

    private Long id;
    private Long companyId;
    private String jobPosition;
    private int reward;
    private String content;
    private String skills;

    public static JobPostResponseDto createFromEntity(JobPost jobPost) {
        return JobPostResponseDto.builder()
                .id(jobPost.getId())
                .companyId(jobPost.getCompanyId())
                .jobPosition(jobPost.getJobPosition())
                .reward(jobPost.getReward())
                .content(jobPost.getContent())
                .skills(jobPost.getSkills())
                .build();
    }
}
