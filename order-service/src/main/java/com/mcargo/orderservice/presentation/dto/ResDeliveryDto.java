package com.mcargo.orderservice.presentation.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ResDeliveryDto (
        UUID deliveryId,
        UUID orderId,
        String deliveryStatus,
        String address,
        Long receiverUserId,
        String receiverSlackId,
        LocalDateTime createdAt,
        Long createdBy,
        List<ResDeliveryRouteDto> deliveryRoutes
){}