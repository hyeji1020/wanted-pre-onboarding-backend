package com.example.domain.jobpost.dto;

import com.example.domain.jobpost.model.JobPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class JobPostResponseDto {

    private Long id;
    private String companyName;
    private String address;
    private String jobPosition;
    private String content;
    private int reward;
    private String skills;

    // 채용 내용 포함
    public static JobPostResponseDto createFromEntity(JobPost jobPost) {
        return JobPostResponseDto.builder()
                .id(jobPost.getId())
                .companyName(jobPost.getCompany().getName())
                .address(jobPost.getCompany().getAddress())
                .jobPosition(jobPost.getJobPosition())
                .content(jobPost.getContent())
                .reward(jobPost.getReward())
                .skills(jobPost.getSkills())
                .build();
    }

    // 채용 내용 미포함
    public static JobPostResponseDto simpleCreateFromEntity(JobPost jobPost) {
        return JobPostResponseDto.builder()
                .id(jobPost.getId())
                .companyName(jobPost.getCompany().getName())
                .address(jobPost.getCompany().getAddress())
                .jobPosition(jobPost.getJobPosition())
                .reward(jobPost.getReward())
                .skills(jobPost.getSkills())
                .build();
    }
}
