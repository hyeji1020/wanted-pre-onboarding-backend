package com.example.domain.jobpost.dto;

import com.example.domain.company.model.Company;
import com.example.domain.jobpost.model.JobPost;
import lombok.Builder;
import lombok.Getter;

@Getter
public class JobPostRequestDto {

    private final Long companyId;
    private final String jobPosition;
    private final int reward;
    private final String content;
    private final String skills;

    @Builder
    public JobPostRequestDto(Long companyId, String jobPosition,
                             int reward, String content,
                             String skills) {
        this.companyId = companyId;
        this.jobPosition = jobPosition;
        this.reward = reward;
        this.content = content;
        this.skills = skills;
    }

    public JobPost toEntity(Company company) {
        return JobPost.builder()
                .company(company)
                .jobPosition(jobPosition)
                .reward(reward)
                .content(content)
                .skills(skills)
                .build();
    }
}
