package com.mcargo.orderservice.presentation.dto;

import java.util.UUID;

public record GetHubProductOrderableResponse(
        UUID hubProductId,
        boolean orderable,
        String message

) {
}
