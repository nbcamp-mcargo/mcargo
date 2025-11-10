package com.mcargo.deliveryservice.presentation.response;

import com.mcargo.deliveryservice.domain.model.DeliveryStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ResDeliveryDto (
        UUID deliveryId,
        UUID orderId,
        DeliveryStatusEnum deliveryStatus,
        String address,
        Long receiverUserId,
        String receiverSlackId,
        LocalDateTime createdAt,
        Long createdBy,
        List<ResDeliveryRouteDto> deliveryRoutes
){}