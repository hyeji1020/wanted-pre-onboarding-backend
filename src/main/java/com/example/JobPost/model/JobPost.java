package com.example.JobPost.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;

    private String jobPosition;
    private int reward;
    private String content;
    private String skills;
//
//    @Builder
//    public JobPost(Long companyId, String jobPosition, int reward, String content, String skills) {
//        this.companyId = companyId;
//        this.jobPosition = jobPosition;
//        this.reward = reward;
//        this.content = content;
//        this.skills = skills;
//    }

}
