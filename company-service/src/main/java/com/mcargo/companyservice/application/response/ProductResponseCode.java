package com.mcargo.companyservice.application.response;

import com.mcargo.common.response.ResponseCode;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
public enum ProductResponseCode implements ResponseCode {

    PRODUCT_CREATE_SUCCESS(HttpStatus.CREATED, "상품 등록에 성공했습니다."),
    PRODUCT_NAME_DUPLICATED(HttpStatus.BAD_REQUEST, "중복된 상품명으로 등록 실패했습니다."),
    PRODUCT_DELETE_SUCCESS(HttpStatus.NO_CONTENT, "상품 삭제에 성공했습니다."),
    PRODUCT_READ_SUCCESS(HttpStatus.OK, "상품 조회에 성공했습니다."),
    PRODUCT_UPDATE_SUCCESS(HttpStatus.OK, "상품 수정에 성공했습니다."),

    PRODUCT_NOT_FOUND(HttpStatus.BAD_REQUEST, "해당 상품이 존재하지 않습니다.");

    private final HttpStatus status;

    private final String message;

    @Override
    public HttpStatus getHttpStatus() {
        return status;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
