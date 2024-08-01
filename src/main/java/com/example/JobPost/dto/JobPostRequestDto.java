package com.example.JobPost.dto;

import com.example.JobPost.model.JobPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class JobPostRequestDto {

    private Long companyId;
    private String jobPosition;
    private int reward;
    private String content;
    private String skills;

    public JobPost toEntity() {
        return JobPost.builder()
                .companyId(companyId)
                .jobPosition(jobPosition)
                .reward(reward)
                .content(content)
                .skills(skills)
                .build();
    }
}
