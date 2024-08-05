package com.example.domain.jobpost.service;

import com.example.domain.company.exception.CompanyErrorCode;
import com.example.domain.company.exception.CompanyException;
import com.example.domain.jobpost.exception.JobPostErrorCode;
import com.example.domain.jobpost.exception.JobPostException;
import com.example.domain.company.model.Company;
import com.example.domain.jobpost.dto.JobPostRequestDto;
import com.example.domain.jobpost.dto.JobPostResponseDto;
import com.example.domain.jobpost.model.JobPost;
import com.example.domain.company.repository.CompanyRepository;
import com.example.domain.jobpost.repository.JobPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class JobPostService {

    private final JobPostRepository jobPostRepository;
    private final CompanyRepository companyRepository;

    public JobPostService(final JobPostRepository jobPostRepository, final CompanyRepository companyRepository) {
        this.jobPostRepository = jobPostRepository;
        this.companyRepository = companyRepository;
    }

    @Transactional
    public JobPostResponseDto createJobPost(JobPostRequestDto jobPostDto){

        Long companyId = jobPostDto.getCompanyId();

        // 회사 ID 유효성 검증
        Company company = companyRepository.findById(companyId).orElseThrow(
                () -> new CompanyException(CompanyErrorCode.NOTFOUND_COMPANY_ID));

        // DTO를 엔티티로 변환
        JobPost jobPostEntity  = jobPostDto.toEntity(company);

        // 엔티티 저장
        JobPost savedJobPost =  jobPostRepository.save(jobPostEntity);

        return JobPostResponseDto.createFromEntity(savedJobPost);
    }


    @Transactional
    public JobPostResponseDto updateJobPost(Long jobPostId, JobPostRequestDto requestDto){

        // 주어진 ID로 기존 채용 공고 조회
        findJobPostById(jobPostId);

        // 회사 ID 유효성 검증
        Company company = companyRepository.findById(requestDto.getCompanyId()).orElseThrow(
                () -> new CompanyException(CompanyErrorCode.NOTFOUND_COMPANY_ID));

        // 기존 객체의 필드를 업데이트
        JobPost updatedJobPost = JobPost.builder()
                .id(jobPostId)
                .company(company)
                .jobPosition(requestDto.getJobPosition())
                .reward(requestDto.getReward())
                .content(requestDto.getContent())
                .skills(requestDto.getSkills())
                .build();

        // 엔티티 저장
        jobPostRepository.save(updatedJobPost);

        return JobPostResponseDto.createFromEntity(updatedJobPost);
    }

    public JobPostResponseDto findJobPostById(Long jobPostId) {

        JobPost jobPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new JobPostException(JobPostErrorCode.NOTFOUND_JOBPOST_ID));

        // 동일한 회사의 다른 채용 공고를 수집
        List<JobPost> allJobPosts = jobPostRepository.findByCompanyId(jobPost.getCompany().getId());
        List<Long> otherJobPostIds = new ArrayList<>();

        for (JobPost post : allJobPosts) {
            if (!post.getId().equals(jobPostId)) {
                otherJobPostIds.add(post.getId());
            }
        }
        return JobPostResponseDto.detailCreateFromEntity(jobPost, otherJobPostIds);
    }

    public List<JobPostResponseDto> getAll() {
        List<JobPost> jobPosts = jobPostRepository.findAll();
        return jobPosts.stream()
                .map(JobPostResponseDto::simpleCreateFromEntity)
                .collect(Collectors.toList());
    }

    @Transactional
    public void delete(Long jobPostId) {
        jobPostRepository.deleteById(jobPostId);
    }
}
