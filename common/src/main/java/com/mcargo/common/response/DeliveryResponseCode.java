package com.mcargo.common.response;

import org.springframework.http.HttpStatus;

public enum DeliveryResponseCode implements ResponseCode {

    DELIVERY_INFO_CREATED(HttpStatus.CREATED, "새로운 배송 정보가 생성되었습니다."),
    DELIVERY_ERROR(HttpStatus.BAD_REQUEST, "배송이 취소되었습니다."),
    DELIVERY_STATUS_UPDATE(HttpStatus.OK, "배송 상태가 변경되었습니다."),
    DELIVERY_LIST_FETCHED(HttpStatus.OK, "배송 목록이 조회되었습니다."),
    DELIVERY_DETAIL_FETCHED(HttpStatus.OK, "배송 정보가 조회되었습니다."),
    DELIVERY_SEARCHED(HttpStatus.OK, "배송 정보 검색에 성공하였습니다.");

    private final HttpStatus httpStatus;

    private final String message;

    DeliveryResponseCode(HttpStatus httpStatus, String message) {
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
