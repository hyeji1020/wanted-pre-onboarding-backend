package com.example.domain.jobpost.dto;

import com.example.domain.jobpost.model.Company;
import com.example.domain.jobpost.model.JobPost;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class JobPostRequestDto {

    private Long companyId;
    private String jobPosition;
    private int reward;
    private String content;
    private String skills;

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
