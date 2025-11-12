package com.mcargo.orderservice.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateOrderRequest(

        @NotBlank(message = "주문의 상태는 비어있을 수 없습니다.")
        String status,

        @NotBlank(message = "요청사항은 비어있을 수 없습니다.")
        String memo,

        @NotNull(message = "주문 총 금액은 비어있을 수 없습니다.")
        Integer totalPrice,

        @NotEmpty(message = "주문 상품 목록은 비어있을 수 없습니다.")
        List<CreateOrderProductRequest> orderProducts

) {
}
