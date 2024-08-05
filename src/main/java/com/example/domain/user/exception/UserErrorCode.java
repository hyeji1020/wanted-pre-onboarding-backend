package com.example.domain.user.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum UserErrorCode {

    NOTFOUND_USER_ID(HttpStatus.NOT_FOUND, "유효하지 않은 사용자 ID입니다.");

    private HttpStatus httpStatus;
    private String message;

    UserErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
