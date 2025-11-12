package com.mcargo.hubservice.presentation.dto;

import java.util.UUID;

public record GetHubProductOrderableRequest(
        //주문에서 검증된 데이터들
        UUID hubProductId,
        UUID productId,
        Integer quantity
) {}