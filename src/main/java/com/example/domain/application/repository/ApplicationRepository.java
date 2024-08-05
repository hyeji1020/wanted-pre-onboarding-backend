package com.example.domain.application.repository;

import com.example.domain.application.model.Application;
import com.example.domain.jobpost.model.JobPost;
import com.example.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    boolean existsByUserAndJobPost(User user, JobPost jobPost);
}
