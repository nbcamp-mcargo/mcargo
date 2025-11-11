package com.mcargo.deliveryservice.domain.response;

import com.mcargo.common.response.ResponseCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum DeliveryResponseCode implements ResponseCode {

    DELIVERY_INFO_CREATED(HttpStatus.CREATED, "새로운 배송 정보가 생성되었습니다."),
    DELIVERY_STATUS_UPDATE(HttpStatus.OK, "배송 상태가 변경되었습니다."),
    DELIVERY_LIST_FETCHED(HttpStatus.OK, "배송 목록이 조회되었습니다."),
    DELIVERY_DETAIL_FETCHED(HttpStatus.OK, "배송 정보가 조회되었습니다."),
    DELIVERY_SEARCHED(HttpStatus.OK, "배송 정보 검색에 성공하였습니다."),
    DELIVERY_STATUS_CANCELED(HttpStatus.OK, "배송이 정상적으로 취소되었습니다."),
    DELIVERY_INFO_DELETED(HttpStatus.OK, "배송이 정상적으로 삭제되었습니다."),

    DELIVERY_ALREADY_EXISTS(HttpStatus.BAD_REQUEST, "이미 배송이 생성된 주문입니다."),
    DELIVERY_NOT_FOUND(HttpStatus.NOT_FOUND, "배송 정보가 존재하지 않습니다."),
    DELIVERY_ALREADY_CANCELED(HttpStatus.BAD_REQUEST, "이미 취소된 배송입니다."),
    DELIVERY_REQUIRED_FIELD_MISSING(HttpStatus.BAD_REQUEST, "필수 정보가 누락되었습니다."),
    DELEVERY_ROUTE_NOT_FOUND(HttpStatus.NOT_FOUND, "배송 경로가 존재하지 않습니다."),
    CANNOT_START_OUT_FOR_DELIVERY(HttpStatus.BAD_REQUEST, "업체 배송을 시작할 수 없습니다.");


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
