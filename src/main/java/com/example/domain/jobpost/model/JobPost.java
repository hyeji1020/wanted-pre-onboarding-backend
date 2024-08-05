package com.example.domain.jobpost.model;

import com.example.domain.application.model.Application;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@Getter
@NoArgsConstructor
@Entity
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(targetEntity = Company.class)
    @JoinColumn(name = "company_id")
    private Company company;

    private String jobPosition;
    private int reward;
    private String content;
    private String skills;

    @OneToMany(mappedBy = "jobPost")
    private List<Application> applications = new ArrayList<>();

    @Builder
    public JobPost(Long id, Company company, String jobPosition, int reward, String content, String skills) {
        this.id = id;
        this.company = company;
        this.jobPosition = jobPosition;
        this.reward = reward;
        this.content = content;
        this.skills = skills;
    }

}
