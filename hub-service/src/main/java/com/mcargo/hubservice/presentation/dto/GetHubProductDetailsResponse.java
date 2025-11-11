package com.mcargo.hubservice.presentation.dto;

import java.util.UUID;

public record GetHubProductDetailsResponse(
        String productName,
        Integer price,
        String description,
        UUID hubId
) {
}
