package com.example.domain.company.exception;

import lombok.Getter;

@Getter
public class CompanyException extends RuntimeException{

    private final CompanyErrorCode companyErrorCode;

    public CompanyException(CompanyErrorCode companyErrorCode) {
        super(companyErrorCode.getMessage());
        this.companyErrorCode = companyErrorCode;
    }
}
