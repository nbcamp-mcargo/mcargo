package com.mcargo.orderservice.presentation.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateOrderRequest(

        @NotBlank(message = "주문의 상태는 비어있을 수 없습니다.")
        String status,

        @NotBlank(message = "요청사항은 비어있을 수 없습니다.")
        String memo,

        @NotBlank(message = "주문 총 금액은 비어있을 수 없습니다.")
        Integer totalPrice
) {
}
