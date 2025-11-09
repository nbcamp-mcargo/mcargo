package com.mcargo.deliveryservice.presentation.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResDeliveryDetailDto(
        UUID orderId,
        UUID fromHubId,
        UUID toHubId,
        String address,
        Long receiverUserId,
        String receiverSlackId,
        LocalDateTime createdAt,
        Long createdBy,
        LocalDateTime updatedAt,
        Long updatedBy
) {}
