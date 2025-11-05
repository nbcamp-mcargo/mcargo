package com.nbcamp.common.response;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum OrderResponseCode implements ResponseCode{

    ORDER_SUCCESS(HttpStatus.OK, "요청하신 주문에 성공했습니다."),
    ORDER_FAILED(HttpStatus.BAD_REQUEST, "주문에 실패했습니다.");

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
