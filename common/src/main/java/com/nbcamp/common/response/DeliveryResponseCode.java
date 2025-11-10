package com.nbcamp.common.response;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum DeliveryResponseCode implements ResponseCode {

    DELIVERY_INFO_CREATED(HttpStatus.CREATED, "새로운 배송 정보가 생성되었습니다."),
    DELIVERY_ERROR(HttpStatus.BAD_REQUEST, "배송이 취소되었습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
