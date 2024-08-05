package com.example.domain.jobpost.dto;

import com.example.domain.company.model.Company;
import com.example.domain.jobpost.model.JobPost;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
public class JobPostRequestDto {


    @NotNull(message = "회사 ID는 필수 항목입니다.")
    private Long companyId;

    @NotBlank(message = "채용 포지션은 필수 항목입니다.")
    @Size(max = 255, message = "채용 포지션은 255자 이하로 입력해야 합니다.")
    private String jobPosition;

    @Min(value = 0, message = "보상 금액은 0 이상이어야 합니다.")
    private int reward;

    @NotBlank(message = "채용 내용은 필수 항목입니다.")
    @Size(max = 5000, message = "채용 내용은 5000자 이하로 입력해야 합니다.")
    private String content;

    @NotBlank(message = "사용 기술은 필수 항목입니다.")
    @Size(max = 1000, message = "사용 기술은 1000자 이하로 입력해야 합니다.")
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
