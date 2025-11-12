package com.mcargo.deliveryservice.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record HubRouteInfo(
        UUID seqStartHubId,
        UUID seqDestHubId,
        Long predictedTime,
        Long predictedDistance,
        int sequence,
        UUID deliveryDriverId
) {}
