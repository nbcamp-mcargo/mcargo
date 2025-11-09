package com.mcargo.deliveryservice.presentation.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record ReqDeliveryDto(
        UUID orderId,
        UUID fromHubId,
        UUID toHubId,
        String address,
        Long receiverUserId,
        String receiverSlackId
) {}