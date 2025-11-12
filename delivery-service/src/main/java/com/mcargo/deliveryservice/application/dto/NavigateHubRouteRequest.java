package com.mcargo.deliveryservice.application.dto;

import org.antlr.v4.runtime.misc.NotNull;

import java.util.UUID;

public record NavigateHubRouteRequest(
        UUID fromHubId,
        UUID toHubId,
        UUID toCompanyId
) {
}