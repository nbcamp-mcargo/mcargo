package com.mcargo.deliveryservice.presentation.response;

import com.mcargo.deliveryservice.domain.model.DeliveryRouteStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResDeliveryRouteDto (
        UUID deliveryRouteId,
        Integer sequence,
        UUID fromHubId,
        UUID toHubId,
        DeliveryRouteStatusEnum deliveryRouteStatus,
        Long predictedDistance,
        Long predictedTime,
        UUID deliveryDriverId,
        LocalDateTime createdAt,
        Long createdBy
){}
