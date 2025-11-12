package com.mcargo.authservice.domain.response;

import com.mcargo.common.response.ResponseCode;
import org.springframework.http.HttpStatus;

public enum DriverResponseCode implements ResponseCode {
    USER_GET_NEXT_DRIVER(HttpStatus.OK, "배송 기사가 배정되었습니다."),
    CREATE_DEIVER_SEQ(HttpStatus.OK, "배송 담당자 생성 시퀀스가 생성되었습니다.");


    private final HttpStatus httpStatus;
    private final String message;

    DriverResponseCode(HttpStatus httpStatus, String s) {
        this.httpStatus = httpStatus;
        this.message = s;
    }

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
