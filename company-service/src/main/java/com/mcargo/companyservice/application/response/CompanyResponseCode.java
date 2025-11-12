package com.mcargo.companyservice.application.response;

import com.mcargo.common.response.ResponseCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@AllArgsConstructor
public enum CompanyResponseCode implements ResponseCode {

    COMPANY_NOT_FOUND(NOT_FOUND, "해당 아이디의 업체가 존재하지 않습니다."),
    COMPANY_READ_SUCCESS(HttpStatus.OK, "업체 조회에 성공했습니다."),
    COMPANY_CREATE_SUCCESS(HttpStatus.CREATED, "업체 생성에 성공했습니다."),
    COMPANY_UPDATE_SUCCESS(HttpStatus.OK, "업체 수정에 성공했습니다.");

    private final HttpStatus status;

    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
