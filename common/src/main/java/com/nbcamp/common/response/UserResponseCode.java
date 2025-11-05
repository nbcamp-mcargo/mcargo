package com.nbcamp.common.response;

import org.springframework.http.HttpStatus;

public enum UserResponseCode implements ResponseCode {

    USER_CREATED(HttpStatus.CREATED, "새로운 사용자가 생성되었습니다."),
    USER_WITHDRAW(HttpStatus.NO_CONTENT, "유저 탈퇴 요청을 성공했습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    UserResponseCode(HttpStatus httpStatus, String message) {
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
