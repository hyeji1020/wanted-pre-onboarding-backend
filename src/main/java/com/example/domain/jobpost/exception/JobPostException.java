package com.example.domain.jobpost.exception;

import lombok.Getter;

@Getter
public class JobPostException extends RuntimeException{

    private final JobPostErrorCode jobPostErrorCode;

    public JobPostException(JobPostErrorCode jobPostErrorCode) {
        super(jobPostErrorCode.getMessage());
        this.jobPostErrorCode = jobPostErrorCode;
    }
}
