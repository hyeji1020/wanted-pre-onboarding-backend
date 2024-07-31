package com.example.JobPost.dto;

import com.example.JobPost.model.JobPost;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.boot.autoconfigure.batch.BatchProperties;

@Getter
@AllArgsConstructor
public class JobPostRequestDto {

    private Long id;

    private Long companyId;

    private String jobPosition;
    private int reward;
    private String content;
    private String skills;

    public JobPostRequestDto(Long companyId, String jobPosition, int reward, String content, String skills) {
        this.companyId = companyId;
        this.jobPosition = jobPosition;
        this.reward = reward;
        this.content = content;
        this.skills = skills;
    }


    @Builder
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
