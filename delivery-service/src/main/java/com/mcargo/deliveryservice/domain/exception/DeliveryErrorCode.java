package com.mcargo.deliveryservice.domain.exception;

import com.mcargo.common.response.ResponseCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

public enum DeliveryErrorCode implements ResponseCode {
    DELIVERY_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 배송이 생성된 주문입니다."),
    DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "배송 정보가 존재하지 않습니다."),
    DELIVERY_ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "이미 취소된 배송입니다."),
    DELIVERY_REQUIRED_FIELD_MISSING(HttpStatus.BAD_REQUEST, "필수 정보가 누락되었습니다."),
    
    
    DELEVERY_ROUTE_NOT_FOUND(HttpStatus.NOT_FOUND, "배송 경로가 존재하지 않습니다."),
    CANNOT_START_OUT_FOR_DELIVERY(HttpStatus.BAD_REQUEST, "업체 배송을 시작할 수 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;

    DeliveryErrorCode(HttpStatus httpStatus, String message) {
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
