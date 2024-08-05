package com.example.domain.company.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum CompanyErrorCode {

    NOTFOUND_COMPANY_ID(HttpStatus.NOT_FOUND, "유효하지 않은 회사 ID 입니다.");

    private HttpStatus httpStatus;
    private String message;

    CompanyErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
