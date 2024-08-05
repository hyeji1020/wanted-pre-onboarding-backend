package com.example.domain.jobpost.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum JobPostErrorCode {

    NOTFOUND_JOBPOST_ID(HttpStatus.NOT_FOUND, "유효하지 않은 채용 공고 ID입니다.");

    private HttpStatus httpStatus;
    private String message;

    JobPostErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
