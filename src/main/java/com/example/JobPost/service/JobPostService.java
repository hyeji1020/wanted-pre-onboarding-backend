package com.example.JobPost.service;

import com.example.JobPost.dto.JobPostRequestDto;
import com.example.JobPost.dto.JobPostResponseDto;
import com.example.JobPost.model.JobPost;
import com.example.JobPost.repository.CompanyRepository;
import com.example.JobPost.repository.JobPostRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

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

    public JobPostResponseDto createJobPost(JobPostRequestDto jobPostDto){

        Long companyId = jobPostDto.getCompanyId();

        // 회사 ID 유효성 검증
        if (companyId == null || !companyRepository.existsById(companyId)) {
            throw new IllegalArgumentException("유효하지 않은 회사 ID입니다.");
        }

        // DTO를 엔티티로 변환
        JobPost jobPostEntity  = jobPostDto.toEntity();

        // 엔티티 저장
        JobPost savedJobPost =  jobPostRepository.save(jobPostEntity);

        return JobPostResponseDto.createFromEntity(savedJobPost);
    }


    public JobPostResponseDto updateJobPost(Long jobPostId, JobPostRequestDto requestDto){

        // 주어진 ID로 기존 채용 공고 조회
        JobPostResponseDto existingJobPost = findJobPostById(jobPostId);

        // 회사 ID 변경 시도 검증
        if (!existingJobPost.getCompanyId().equals(requestDto.getCompanyId())) {
            throw new IllegalArgumentException("회사 ID는 변경할 수 없습니다.");
        }

        // 기존 객체의 필드를 업데이트
        JobPost updateDto = JobPost.builder()
                .id(existingJobPost.getId())
                .companyId(existingJobPost.getCompanyId())
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
                .map(JobPostResponseDto::createFromEntity)
                .collect(Collectors.toList());
    }

    public void delete(Long jobPostId) {
        jobPostRepository.deleteById(jobPostId);
    }
}
