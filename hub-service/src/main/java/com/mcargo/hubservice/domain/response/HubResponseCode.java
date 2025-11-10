package com.mcargo.hubservice.domain.response;

import com.mcargo.common.response.ResponseCode;
import org.springframework.http.HttpStatus;

public enum HubResponseCode implements ResponseCode {

    // 성공
    HUB_CREATED(HttpStatus.CREATED, "성공적으로 허브가 생성되었습니다."),
    HUB_PRODUCT_CREATED(HttpStatus.CREATED, "성공적으로 허브상품이 생성되었습니다."),
    Hub_ROUTE_CREATED(HttpStatus.CREATED, "성공적으로 허브경로가 생성되었습니다."),
    HUB_OK(HttpStatus.OK, "해당 요청을 성공했습니다."),
    HUB_PRODUCT_ORDERABLE(HttpStatus.OK, "해당 상품은 주문 가능한 상품입니다."),

    //실패
    HUB_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 허브를 찾을 수 없습니다."),
    HUB_PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "해당 허브상품을 찾을 수 없습니다."),
    HUB_DRIVER_NOT_FOUND(HttpStatus.NOT_FOUND, "현재 업무 가능한 업체배송담당자가 없습니다."),
    ;

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
