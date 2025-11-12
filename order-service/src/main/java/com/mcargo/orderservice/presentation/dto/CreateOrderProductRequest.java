package com.mcargo.orderservice.presentation.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CreateOrderProductRequest(

        @NotNull(message = "허브상품 ID는 필수입니다.")
        UUID hubProductId,

        @NotNull(message = "상품 ID는 필수입니다.")
        UUID productId,

        @NotNull(message = "수량은 필수입니다.")
        @Min(value = 1, message = "수량은 1개 이상이어야 합니다.")
        Integer quantity
) {}
