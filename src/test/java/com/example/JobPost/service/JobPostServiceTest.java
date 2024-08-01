package com.example.JobPost.service;

import com.example.JobPost.dto.JobPostRequestDto;
import com.example.JobPost.dto.JobPostResponseDto;
import com.example.JobPost.model.Company;
import com.example.JobPost.model.JobPost;
import com.example.JobPost.repository.CompanyRepository;
import com.example.JobPost.repository.JobPostRepository;
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

        savedCompany = new Company(1L, "네이버", "판교");
        savedCompany =companyRepository.save(savedCompany);

        // JobPost 객체 생성 및 저장
        JobPost jobPost = JobPost.builder()
                .companyId(savedCompany.getId())
                .jobPosition("주니어 백엔드 개발자")
                .reward(10_000)
                .content("자세한 내용")
                .skills("Java")
                .build();
        savedJobPost = jobPostRepository.save(jobPost);
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
    @DisplayName("채용 공고 생성 시 통과")
    void should_Create_JobPost_Successfully() {

        // given
        JobPostRequestDto createDto = JobPostRequestDto.builder()
                .companyId(savedCompany.getId())
                .jobPosition("수정된 주니어 백엔드 개발자")
                .reward(15_000)
                .content("자세한 내용")
                .skills("Java, Spring")
                .build();

        // when
        JobPostResponseDto createdPost = jobPostService.createJobPost(createDto);

        // then
        assertNotNull(createdPost.getId());
        assertEquals(createdPost.getJobPosition(), createDto.getJobPosition());
        assertEquals(createdPost.getCompanyId(), createDto.getCompanyId());

    }

    @Test
    @DisplayName("유효하지 않은 companyId로 채용 공고 생성 시 예외 발생")
    void should_ThrowException_Create_With_Invalid_CompanyId() {

        // given
        Long invalidCompanyId = 999L;
        JobPostRequestDto createDto = JobPostRequestDto.builder()
                .companyId(invalidCompanyId)
                .jobPosition("수정된 주니어 백엔드 개발자")
                .reward(15_000)
                .content("자세한 내용")
                .skills("Java, Spring")
                .build();

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            jobPostService.createJobPost(createDto);
        });

    }

    @Test
    @DisplayName("채용공고 수정 시 성공")
    void should_Update_JobPost_Successfully() {

        // given
        JobPostRequestDto updateDto = JobPostRequestDto.builder()
                .companyId(savedJobPost.getCompanyId())
                .jobPosition("수정된 주니어 백엔드 개발자")
                .reward(15_000)
                .content("수정된 내용")
                .skills("Java, Spring")
                .build();

        // when
        JobPostResponseDto updatedPost = jobPostService.updateJobPost(savedJobPost.getId(), updateDto);

        // then
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
        assertThrows(IllegalArgumentException.class, () -> {
            jobPostService.findJobPostById(invalidPostId);
        });
    }

    @Test
    @DisplayName("채용 공고 목록 조회 시 성공")
    void should_Select_JobPosts_Successfully() {

        // given
        JobPost jobPost2 = JobPost.builder()
                .companyId(savedCompany.getId())
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
                .companyId(2L) // 기존과 다른 회사 ID
                .jobPosition("프론트엔드 개발자")
                .reward(15000)
                .content("프론트엔드 개발자 모집")
                .skills("JavaScript, React")
                .build();

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            jobPostService.updateJobPost(savedJobPost.getId(), updateDto);
        });
    }
}