package com.example.domain.jobpost.dto;

import com.example.domain.jobpost.model.JobPost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JobPostResponseDto {

    private final Long id;
    private final String companyName;
    private final String address;
    private final String jobPosition;
    private final String content;
    private final int reward;
    private final String skills;

    @Builder
    public JobPostResponseDto(Long id, String companyName, String address, String jobPosition, String content, int reward, String skills) {
        this.id = id;
        this.companyName = companyName;
        this.address = address;
        this.jobPosition = jobPosition;
        this.content = content;
        this.reward = reward;
        this.skills = skills;
    }


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
