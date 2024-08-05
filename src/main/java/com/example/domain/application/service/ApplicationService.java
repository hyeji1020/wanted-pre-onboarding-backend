package com.example.domain.application.service;

import com.example.domain.application.dto.ApplicationRequestDto;
import com.example.domain.application.model.Application;
import com.example.domain.application.repository.ApplicationRepository;
import com.example.domain.jobpost.model.JobPost;
import com.example.domain.jobpost.repository.JobPostRepository;
import com.example.domain.user.model.User;
import com.example.domain.user.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
public class ApplicationService {

    private JobPostRepository jobPostRepository;

    private ApplicationRepository applicationRepository;

    private UserRepository userRepository;

    public ApplicationService(JobPostRepository jobPostRepository, ApplicationRepository applicationRepository, UserRepository userRepository) {
        this.jobPostRepository = jobPostRepository;
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Application createApplication(ApplicationRequestDto requestDto) {

        // 사용자와 채용 공고 찾기
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 사용자 ID입니다."));
        JobPost jobPost = jobPostRepository.findById(requestDto.getJobPostId())
                .orElseThrow(() -> new IllegalArgumentException("유효하지 않은 채용 공고 ID입니다."));

        // 사용자가 해당 채용 공고에 이미 지원했는지 확인
        boolean alreadyApplied = applicationRepository.existsByUserAndJobPost(user, jobPost);
        if (alreadyApplied) {
            throw new IllegalArgumentException("사용자는 이 채용 공고에 이미 지원했습니다.");
        }

        // 지원서 생성 및 저장
        Application application = new Application(user, jobPost, LocalDate.now());

        return applicationRepository.save(application);
    }
}
