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

    @BeforeEach
    public void setUp() {
        companyRepository.deleteAll();
        jobPostRepository.deleteAll();
        Company company = new Company(1L, "네이버", "판교");
        companyRepository.save(company);
    }

    @Test
    @DisplayName("채용 공고 생성 시 통과")
    void should_Create_JobPost_Successfully() {

        // given
        Company company = companyRepository.findAll().get(0);
        JobPostRequestDto requestDto = new JobPostRequestDto(company.getId(), "주니어 백엔드 개발자", 10_000, "자세한 내용", "Java");

        // when
        JobPostResponseDto createdPost = jobPostService.createJobPost(requestDto);

        // then
        assertNotNull(createdPost.getId());
        assertEquals(createdPost.getJobPosition(), requestDto.getJobPosition());
        assertEquals(createdPost.getCompanyId(), company.getId());

    }

    @Test
    @DisplayName("유효하지 않은 companyId로 채용 공고 생성 시 예외 발생")
    void should_ThrowException_Create_With_Invalid_CompanyId() {

        // given
        Long invalidCompanyId = 999L;
        JobPostRequestDto jobPostDto = new JobPostRequestDto(invalidCompanyId, "주니어 백엔드 개발자", 10_000, "자세한 내용", "Java");

        // when & then
        assertThrows(IllegalArgumentException.class, () -> {
            jobPostService.createJobPost(jobPostDto);
        });

    }

    @Test
    @DisplayName("채용공고 수정 시 성공")
    void should_Update_JobPost_Successfully() {

        // given
        Company company = companyRepository.findAll().get(0);
        JobPost jobPost = JobPost.builder()
                .companyId(company.getId())
                .jobPosition("주니어 백엔드 개발자")
                .reward(10_000)
                .content("자세한 내용")
                .skills("Java")
                .build();

        jobPostRepository.save(jobPost);

        JobPostRequestDto updateDto = new JobPostRequestDto(jobPost.getId(), "주니어 백엔드 개발자", 10_000, "자세한 내용_수정하기", "Java");

        // when
        JobPostResponseDto updatedPost = jobPostService.updateJobPost(jobPost.getId(), updateDto);

        // then
        assertEquals("자세한 내용_수정하기", updatedPost.getContent());

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
        Company company = companyRepository.findAll().get(0);
        JobPost jobPost1 = JobPost.builder()
                .companyId(company.getId())
                .jobPosition("경력 개발자")
                .reward(10_00000)
                .content("테스트")
                .skills("spring")
                .build();

        JobPost jobPost2 = JobPost.builder()
                .companyId(company.getId())
                .jobPosition("주니어 개발자")
                .reward(20_00000)
                .content("테스트2")
                .skills("spring")
                .build();

        JobPost savedJobPost1 = jobPostRepository.save(jobPost1);
        JobPost savedJobPost2 = jobPostRepository.save(jobPost2);

        // when
        List<JobPostResponseDto> result = jobPostService.getAll();

        // then
        assertThat(result).isNotNull();
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).extracting(JobPostResponseDto::getId)
                .containsExactlyInAnyOrder(savedJobPost1.getId(), savedJobPost2.getId());

    }

    @Test
    @DisplayName("채용 공고 삭제 시 성공")
    void should_Delete_JobPost_Successfully() {

        // given
        Company company = companyRepository.findAll().get(0);
        JobPost jobPost = JobPost.builder()
                .companyId(company.getId())
                .jobPosition("주니어 백엔드 개발자")
                .reward(10_000)
                .content("자세한 내용")
                .skills("Java")
                .build();
        JobPost savedPost = jobPostRepository.save(jobPost);
        Long postId = savedPost.getId();

        // when
        jobPostService.delete(postId);

        // then
        assertFalse(jobPostRepository.findById(jobPost.getId()).isPresent());
    }
}