package com.example.domain.application.service;

import com.example.domain.application.dto.ApplicationRequestDto;
import com.example.domain.application.dto.ApplicationResponseDto;
import com.example.domain.application.exception.ApplyException;
import com.example.domain.application.repository.ApplicationRepository;
import com.example.domain.jobpost.exception.JobPostException;
import com.example.domain.company.model.Company;
import com.example.domain.jobpost.model.JobPost;
import com.example.domain.company.repository.CompanyRepository;
import com.example.domain.jobpost.repository.JobPostRepository;
import com.example.domain.user.exception.UserException;
import com.example.domain.user.model.User;
import com.example.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class ApplicationServiceTest {

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private UserRepository userRepository;

    private Company savedCompany;
    private User savedUser;
    private JobPost savedJobPost;

    @BeforeEach
    public void setUp() {

        applicationRepository.deleteAll();
        userRepository.deleteAll();
        companyRepository.deleteAll();
        jobPostRepository.deleteAll();

        // Company 객체 생성 및 저장
        savedCompany = new Company(1L, "네이버", "판교");
        savedCompany = companyRepository.save(savedCompany);

        // User 객체 생성 및 저장
        savedUser = new User(1L, "홍길동", 28);
        savedUser = userRepository.save(savedUser);

        // JobPost 객체 생성 및 저장
        JobPost jobPost = JobPost.builder()
                .company(savedCompany)
                .jobPosition("주니어 백엔드 개발자")
                .reward(10_000)
                .content("자세한 내용")
                .skills("Java")
                .build();
        savedJobPost = jobPostRepository.save(jobPost);
    }

    @Test
    @DisplayName("채용 공고 지원 시 성공")
    void should_Create_Apply_Successfully() {

        // given
        ApplicationRequestDto requestDto = new ApplicationRequestDto(savedUser.getId(), savedJobPost.getId());

        // when
        ApplicationResponseDto result = applicationService.createApplication(requestDto);

        // then
        assertNotNull(result);
        assertEquals(savedUser.getId(), result.getUserId());
        assertEquals(savedJobPost.getId(), result.getJobPostId());
    }

    @Test
    @DisplayName("이미 지원한 채용 공고 예외 발생")
    void should_ThrowException_createApplication_UserAlreadyApplied() {

        // given
        ApplicationRequestDto requestDto = new ApplicationRequestDto(savedUser.getId(), savedJobPost.getId());
        applicationService.createApplication(requestDto);

        // when & then
        ApplyException exception = assertThrows(ApplyException.class, () -> {
            applicationService.createApplication(requestDto);
        });

        assertEquals("사용자는 이미 해당 채용 공고에 지원했습니다.", exception.getMessage());
    }

    @Test
    @DisplayName("유효하지 않은 userId로 지원 시 예외 발생")
    void should_ThrowException_createApplication_InvalidUserId() {

        // given
        Long invalidUserId = -1L;
        ApplicationRequestDto requestDto = new ApplicationRequestDto(invalidUserId, savedJobPost.getId());

        // when & then
        UserException exception = assertThrows(UserException.class, () -> {
            applicationService.createApplication(requestDto);
        });

        assertEquals("유효하지 않은 사용자 ID입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("유효하지 않은 jobPostId로 지원 시 예외 발생")
    void should_ThrowException_createApplication_InvalidJobPostId() {

        // given
        Long invalidJobPostId = -1L;
        ApplicationRequestDto requestDto = new ApplicationRequestDto(savedUser.getId(), invalidJobPostId);

        // when & then
        JobPostException exception = assertThrows(JobPostException.class, () -> {
            applicationService.createApplication(requestDto);
        });

        assertEquals("유효하지 않은 채용 공고 ID입니다.", exception.getMessage());
    }
}