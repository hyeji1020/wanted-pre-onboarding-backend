package com.example.domain.jobpost.controller;

import com.example.domain.jobpost.dto.JobPostRequestDto;
import com.example.domain.jobpost.dto.JobPostResponseDto;
import com.example.domain.jobpost.service.JobPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/job-posts")
public class JobPostController {

    private final JobPostService jobPostService;

    public JobPostController(final JobPostService jobPostService) {
        this.jobPostService = jobPostService;
    }

    // 채용공고 등록
    @PostMapping
    public ResponseEntity<JobPostResponseDto> createJobPost(@RequestBody JobPostRequestDto jobPostRequestDto){
        JobPostResponseDto createdPost = jobPostService.createJobPost(jobPostRequestDto);
        return ResponseEntity.ok(createdPost);
    }

    // 채용 공고 조회
    @GetMapping("/{jobPostId}")
    public ResponseEntity<JobPostResponseDto> getJobPost(@PathVariable Long jobPostId) {
        JobPostResponseDto jobPost = jobPostService.findJobPostById(jobPostId);
        return ResponseEntity.ok(jobPost);
    }

    // 채용 공고 목록 조회
    @GetMapping
    public ResponseEntity<List<JobPostResponseDto>> getJobPostList() {
        List<JobPostResponseDto> jobPostList = jobPostService.getAll();
        return ResponseEntity.ok(jobPostList);
    }

    @PutMapping("/{jobPostId}")
    public ResponseEntity<JobPostResponseDto> updateJobPost(@PathVariable Long jobPostId, @RequestBody JobPostRequestDto jobPostRequestDto) {
        JobPostResponseDto updatedPost = jobPostService.updateJobPost(jobPostId, jobPostRequestDto);
        return ResponseEntity.ok(updatedPost);
    }

    @DeleteMapping("/{jobPostId}")
    public ResponseEntity<?> deleteJobPost(@PathVariable Long jobPostId) {
        jobPostService.delete(jobPostId);
        return ResponseEntity.ok().build();
    }
}
