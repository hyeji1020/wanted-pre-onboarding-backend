package com.example.JobPost.service;

import com.example.JobPost.dto.JobPostRequestDto;
import com.example.JobPost.model.JobPost;
import com.example.JobPost.repository.CompanyRepository;
import com.example.JobPost.repository.JobPostRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class JobPostService {

    private JobPostRepository jobPostRepository;
    private CompanyRepository companyRepository;

    public JobPostService(JobPostRepository jobPostRepository, CompanyRepository companyRepository) {
        this.jobPostRepository = jobPostRepository;
        this.companyRepository = companyRepository;
    }

    public JobPost createJobPost(JobPostRequestDto jobPostDto){

        Long companyId = jobPostDto.getCompanyId();

        // 회사 ID 유효성 검증
        if (companyId == null || !companyRepository.existsById(companyId)) {
            throw new IllegalArgumentException("유효하지 않은 회사 ID입니다.");
        }

        // DTO를 엔티티로 변환
        JobPost jobPost = jobPostDto.toEntity();

        return jobPostRepository.save(jobPost);
    }


    public JobPost updateJobPost(Long jobPostId, JobPostRequestDto requestDto){

        // 주어진 ID로 기존 채용 공고 조회
        getById(jobPostId);

        // DTO를 엔티티로 변환
        JobPost updatedJobPost = requestDto.toEntity();

        return jobPostRepository.save(updatedJobPost);
    }

    public JobPost getById(Long jobPostId) {

        if(!jobPostRepository.existsById(jobPostId)){
            throw new IllegalArgumentException("유효하지 않은 채용 공고 아이디 입니다.");
        }

        return jobPostRepository.getById(jobPostId);
    }

    public List<JobPost> getAll() {
        return jobPostRepository.findAll();
    }

    public void delete(Long jobPostId) {
        jobPostRepository.deleteById(jobPostId);
    }
}
