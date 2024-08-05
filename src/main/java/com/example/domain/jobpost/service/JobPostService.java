package com.example.domain.jobpost.service;

import com.example.domain.jobpost.model.Company;
import com.example.domain.jobpost.dto.JobPostRequestDto;
import com.example.domain.jobpost.dto.JobPostResponseDto;
import com.example.domain.jobpost.model.JobPost;
import com.example.domain.jobpost.repository.CompanyRepository;
import com.example.domain.jobpost.repository.JobPostRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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
                () -> new IllegalArgumentException("유효하지 않은 회사 ID 입니다."));

        // DTO를 엔티티로 변환
        JobPost jobPostEntity  = jobPostDto.toEntity(company);

        // 엔티티 저장
        JobPost savedJobPost =  jobPostRepository.save(jobPostEntity);

        return JobPostResponseDto.createFromEntity(savedJobPost);
    }


    @Transactional
    public JobPostResponseDto updateJobPost(Long jobPostId, JobPostRequestDto requestDto){

        // 주어진 ID로 기존 채용 공고 조회
        JobPostResponseDto existingJobPost = findJobPostById(jobPostId);

        // 회사 ID 유효성 검증
        Company company = companyRepository.findById(requestDto.getCompanyId()).orElseThrow(
                () -> new IllegalArgumentException("유효하지 않은 회사 ID 입니다."));

        // 기존 객체의 필드를 업데이트
        JobPost updateDto = JobPost.builder()
                .company(company)
                .jobPosition(requestDto.getJobPosition())
                .reward(requestDto.getReward())
                .content(requestDto.getContent())
                .skills(requestDto.getSkills())
                .build();

        // 엔티티 저장
        JobPost updatedJobPost = jobPostRepository.save(updateDto);

        return JobPostResponseDto.createFromEntity(updatedJobPost);
    }

    public JobPostResponseDto findJobPostById(Long jobPostId) {
         JobPost findPost = jobPostRepository.findById(jobPostId)
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 채용 공고 아이디입니다."));

         return JobPostResponseDto.createFromEntity(findPost);
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
