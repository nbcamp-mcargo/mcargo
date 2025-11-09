package com.mcargo.deliveryservice.presentation.response;

import com.mcargo.deliveryservice.domain.model.DeliveryStatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResDeliveryDetailDto(
        UUID deliveryId,
        UUID orderId,
        DeliveryStatusEnum deliveryStatus,
        String address,
        Long receiverUserId,
        String receiverSlackId,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime updatedAt,
        Long updatedBy
) {}
