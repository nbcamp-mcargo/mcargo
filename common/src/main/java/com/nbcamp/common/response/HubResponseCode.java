package com.nbcamp.common.response;

import org.springframework.http.HttpStatus;

public enum HubResponseCode implements ResponseCode {

    HUB_CREATED(HttpStatus.CREATED, "새로운 허브 정보가 생성되었습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    HubResponseCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
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
