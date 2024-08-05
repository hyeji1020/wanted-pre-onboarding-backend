package com.example.domain.user.model;

import com.example.domain.application.model.Application;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name")
    private String username;
    private int age;

    @OneToOne(mappedBy = "user")
    private Application application;

    @Builder
    public User(Long id, String username, int age, Application application){
        this.id = id;
        this.username = username;
        this.age = age;
        this.application = application;
    }

}
