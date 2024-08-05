package com.example.domain.jobpost.service;

import com.example.domain.company.exception.CompanyException;
import com.example.domain.jobpost.dto.JobPostRequestDto;
import com.example.domain.jobpost.dto.JobPostResponseDto;
import com.example.domain.jobpost.exception.JobPostException;
import com.example.domain.jobpost.model.Company;
import com.example.domain.jobpost.model.JobPost;
import com.example.domain.jobpost.repository.CompanyRepository;
import com.example.domain.jobpost.repository.JobPostRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
class JobPostServiceTest {

    @Autowired
    private JobPostService jobPostService;

    @Autowired
    private JobPostRepository jobPostRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Company savedCompany;
    private JobPost savedJobPost;

    @BeforeEach
    public void setUp() {
        companyRepository.deleteAll();
        jobPostRepository.deleteAll();

        // Company 객체 생성 및 저장
        savedCompany = new Company(1L, "네이버", "판교");
        savedCompany = companyRepository.save(savedCompany);

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
    @DisplayName("채용 공고 생성 시 통과")
    void should_Create_JobPost_Successfully() {

        // given
        JobPostRequestDto createDto = JobPostRequestDto.builder()
                .companyId(savedCompany.getId())
                .jobPosition("프론트엔드 개발자")
                .reward(15_000)
                .content("자세한 내용")
                .skills("Java, Spring")
                .build();

        // when
        JobPostResponseDto createdPost = jobPostService.createJobPost(createDto);

        // then
        assertNotNull(createdPost.getId());
        assertEquals(createdPost.getJobPosition(), createDto.getJobPosition());
    }

    @Test
    @DisplayName("채용 공고 조회 시 성공")
    void should_Get_JobPost_Successfully() {

        // when
        JobPostResponseDto result = jobPostService.findJobPostById(savedJobPost.getId());

        // then
        assertNotNull(result);
        assertEquals(savedJobPost.getId(), result.getId());
    }

    @Test
    @DisplayName("유효하지 않은 companyId로 채용 공고 생성 시 예외 발생")
    void should_ThrowException_Create_With_Invalid_CompanyId() {

        // given
        Long invalidCompanyId = 999L;
        JobPostRequestDto createDto = JobPostRequestDto.builder()
                .companyId(invalidCompanyId)
                .jobPosition("주니어 백엔드 개발자")
                .reward(15_000)
                .content("자세한 내용")
                .skills("Java, Spring")
                .build();

        // when & then
        assertThrows(CompanyException.class, () -> {
            jobPostService.createJobPost(createDto);
        });
    }

    @Test
    @DisplayName("채용 공고 수정 시 성공")
    void should_Update_JobPost_Successfully() {

        // given
        JobPostRequestDto updateDto = JobPostRequestDto.builder()
                .companyId(savedCompany.getId())
                .jobPosition("수정된 주니어 백엔드 개발자")
                .reward(15_000)
                .content("수정된 내용")
                .skills("Java, Spring")
                .build();

        // when
        JobPostResponseDto updatedPost = jobPostService.updateJobPost(savedJobPost.getId(), updateDto);

        // then
        assertEquals(savedJobPost.getId(), updatedPost.getId());
        assertEquals(updateDto.getJobPosition(), updatedPost.getJobPosition());
        assertEquals(updateDto.getReward(), updatedPost.getReward());
        assertEquals(updateDto.getContent(), updatedPost.getContent());
    }

    @Test
    @DisplayName("유효하지 않은 JobPostId로 채용 공고 조회 시 예외 발생")
    void should_ThrowException_Select_With_Invalid_JobPostId() {

        // given
        Long invalidPostId = 999L;

        // when & then
        assertThrows(JobPostException.class, () -> {
            jobPostService.findJobPostById(invalidPostId);
        });
    }

    @Test
    @DisplayName("채용 공고 목록 조회 시 성공")
    void should_Select_JobPosts_Successfully() {

        // given
        JobPost jobPost2 = JobPost.builder()
                .company(savedCompany)
                .jobPosition("주니어 개발자")
                .reward(20_00000)
                .content("자세한 내용")
                .skills("spring")
                .build();

        JobPost savedJobPost2 = jobPostRepository.save(jobPost2);

        // when
        List<JobPostResponseDto> result = jobPostService.getAll();

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).extracting(JobPostResponseDto::getId)
                .containsExactlyInAnyOrder(savedJobPost.getId(), savedJobPost2.getId());
    }

    @Test
    @DisplayName("채용 공고 삭제 시 성공")
    void should_Delete_JobPost_Successfully() {

        // when
        jobPostService.delete(savedJobPost.getId());

        // then
        assertFalse(jobPostRepository.findById(savedJobPost.getId()).isPresent());
    }

    @Test
    @DisplayName("회사 ID 변경 시 예외 발생")
    public void should_ThrowException_When_CompanyIdChanged() {

        // given
        JobPostRequestDto updateDto = JobPostRequestDto.builder()
                .companyId(2L)
                .jobPosition("프론트엔드 개발자")
                .reward(15000)
                .content("수정된 내용")
                .skills("JavaScript, React")
                .build();

        // when & then
        assertThrows(CompanyException.class, () -> {
            jobPostService.updateJobPost(savedJobPost.getId(), updateDto);
        });
    }

    @Test
    @DisplayName("채용 공고 상세 조회 시 회사의 다른 공고 목록 포함하여 반환")
    void should_Return_JobPostDetail_With_OtherJobPostIds() {

        // given
        JobPost jobPost2 = JobPost.builder()
                .company(savedCompany)
                .jobPosition("주니어 개발자")
                .reward(20_00000)
                .content("자세한 내용")
                .skills("spring")
                .build();
        JobPost savedPost = jobPostRepository.save(jobPost2);

        // when
        JobPostResponseDto result = jobPostService.findJobPostById(savedJobPost.getId());

        // then
        assertNotNull(result);
        assertEquals(1, result.getOtherJobPostIds().size());
        assertTrue(result.getOtherJobPostIds().contains(savedPost.getId()));
    }
}