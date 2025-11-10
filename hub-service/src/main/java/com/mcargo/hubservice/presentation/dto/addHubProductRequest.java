package com.mcargo.hubservice.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record addHubProductRequest(
        // 업체 관리자가 업체상품을 허브에 적재? 하겠다는 요청
        @NotBlank(message = "적재할 허브의 id를 입력하세요.")
        UUID hubId,

        @NotBlank(message = "등록할 업체상품의 id를 입력하세요.")
        UUID productId, // 업체상품의 uuid

        @NotBlank(message = "등록할 상품의 재고를 입력하세요.")
        Integer stock
) {
}
