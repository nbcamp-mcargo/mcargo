package com.mcargo.hubservice.presentation.dto;

import com.mcargo.hubservice.domain.entity.HubProductStatus;

import java.util.UUID;

public record GetHubProductResponse(
        UUID hubId,
        UUID productId,
        HubProductStatus status,
        Integer stock

) {
}
