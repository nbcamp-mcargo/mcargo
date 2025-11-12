package com.mcargo.orderservice.presentation.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResDeliveryRouteDto (
        UUID deliveryRouteId,
        Integer sequence,
        UUID fromHubId,
        UUID toHubId,
//        DeliveryRouteStatusEnum deliveryRouteStatus,
        Long predictedDistance,
        Long predictedTime,
        UUID deliveryDriverId,
        LocalDateTime createdAt,
        Long createdBy
){}
