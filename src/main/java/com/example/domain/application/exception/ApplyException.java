package com.example.domain.application.exception;

import lombok.Getter;

@Getter
public class ApplyException extends RuntimeException {

    private final ApplyErrorCode applyErrorCode;

    public ApplyException(ApplyErrorCode applyErrorCode) {
        super(applyErrorCode.getMessage());
        this.applyErrorCode = applyErrorCode;
    }
}
