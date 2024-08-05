package com.example.domain.application.model;

import com.example.domain.jobpost.model.JobPost;
import com.example.domain.user.model.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Entity(name = "application")
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_post_id")
    private JobPost jobPost;

    private LocalDate applicationDate;

    @Builder
    public Application(User user, JobPost jobPost, LocalDate applicationDate) {
        this.user = user;
        this.jobPost = jobPost;
        this.applicationDate = applicationDate;
    }
}
