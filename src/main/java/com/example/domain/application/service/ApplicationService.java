package com.example.domain.application.service;

import com.example.domain.application.dto.ApplicationRequestDto;
import com.example.domain.application.dto.ApplicationResponseDto;
import com.example.domain.application.exception.ApplyErrorCode;
import com.example.domain.application.exception.ApplyException;
import com.example.domain.application.model.Application;
import com.example.domain.application.repository.ApplicationRepository;
import com.example.domain.jobpost.exception.JobPostErrorCode;
import com.example.domain.jobpost.exception.JobPostException;
import com.example.domain.jobpost.model.JobPost;
import com.example.domain.jobpost.repository.JobPostRepository;
import com.example.domain.user.exception.UserErrorCode;
import com.example.domain.user.exception.UserException;
import com.example.domain.user.model.User;
import com.example.domain.user.repository.UserRepository;
import org.springframework.context.ApplicationContextException;
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
    public ApplicationResponseDto createApplication(ApplicationRequestDto requestDto) {

        // 사용자와 채용 공고 찾기
        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new UserException(UserErrorCode.NOTFOUND_USER_ID));
        JobPost jobPost = jobPostRepository.findById(requestDto.getJobPostId())
                .orElseThrow(() -> new JobPostException(JobPostErrorCode.NOTFOUND_JOBPOST_ID));

        // 사용자가 해당 채용 공고에 이미 지원했는지 확인
        boolean alreadyApplied = applicationRepository.existsByUserAndJobPost(user, jobPost);
        if (alreadyApplied) {
            throw new ApplyException(ApplyErrorCode.ALREADY_APPLY);
        }

        // 지원서 생성 및 저장
        Application application = new Application(user, jobPost, LocalDate.now());

        Application savedApplication = applicationRepository.save(application);

        return ApplicationResponseDto.createFromEntity(savedApplication);
    }
}
