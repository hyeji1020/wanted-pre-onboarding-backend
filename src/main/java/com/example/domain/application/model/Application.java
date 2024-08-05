package com.example.domain.application.model;

import com.example.domain.jobpost.model.JobPost;
import com.example.domain.user.model.User;
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "job_post_id")
    private JobPost jobPost;

    private LocalDate applicationDate;

    public Application(User user, JobPost jobPost, LocalDate now) {
        this.user = user;
        this.jobPost = jobPost;
        this.applicationDate = now;
    }
}
