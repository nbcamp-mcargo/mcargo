package com.mcargo.orderservice.domain.response;

import com.mcargo.common.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
@Getter
@AllArgsConstructor
public enum OrderResponseCode implements ResponseCode {

    //성공
    ORDER_OK(HttpStatus.OK, "요청하신 주문에 대한 응답입니다."),
    ORDER_UPDATED(HttpStatus.OK, "요청하신 주문을 업데이트 했습니다."),
    ORDER_DELETED(HttpStatus.OK, "요청하신 주문을 삭제 했습니다."),
    ORDER_FOUND(HttpStatus.FOUND, "주문을 찾았습니다."),
    ORDER_PRODUCT_CREATED(HttpStatus.CREATED, "요청하신 상품 주문에 성공했습니다."),
    ORDER_PRODUCT_UPDATED(HttpStatus.OK, "요청하신 상품을 업데이트 했습니다."),
    ORDER_PRODUCT_DELETED(HttpStatus.OK, "요청하신 상품을 삭제 했습니다."),
    ORDER_PRODUCT_FOUND(HttpStatus.OK, "요청하신 상품을 찾았습니다."),

    //실패
    ORDER_FAILED(HttpStatus.BAD_REQUEST, "주문에 실패했습니다."),
    ORDER_NOT_FOUND(HttpStatus.NOT_FOUND, "요청하신 주문을 찾지 못했습니다."),
    ORDER_PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, "요청하신 주문 상품을 찾지 못했습니다.");

    private final HttpStatus httpStatus;

    private final String message;

//    @Override
//    public HttpStatus getHttpStatus() {
//        return httpStatus;
//    }
//
//    @Override
//    public String getMessage() {
//        return message;
//    }
}
