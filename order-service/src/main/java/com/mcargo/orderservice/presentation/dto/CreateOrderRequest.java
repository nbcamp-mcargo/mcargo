package com.mcargo.orderservice.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public record CreateOrderRequest(

        @NotBlank(message = "요청사항은 비어있을 수 없습니다.")
        String memo,

        @NotEmpty(message = "주문 상품 목록은 비어있을 수 없습니다.")
        List<CreateOrderProductRequest> orderProducts

) {
}
