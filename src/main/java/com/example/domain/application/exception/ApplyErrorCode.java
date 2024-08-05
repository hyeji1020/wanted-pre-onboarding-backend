package com.example.domain.application.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ApplyErrorCode {

    ALREADY_APPLY(HttpStatus.CONFLICT, "사용자는 이미 해당 채용 공고에 지원했습니다.");

    private HttpStatus httpStatus;
    private String message;

    ApplyErrorCode(HttpStatus httpStatus, String message){
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
