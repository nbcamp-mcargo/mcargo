package com.mcargo.deliveryservice.presentation.response;

import com.mcargo.deliveryservice.domain.model.DeliveryRouteStatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResDeliveryRouteDetailDto(
   UUID deliveryRouteId,
   int sequence,
   UUID fromHubId,
   UUID toHubId,
   int estimatedDistance,
   LocalDateTime estimatedTime,
   int actualDistance,
   LocalDateTime actualTime,
   UUID deliveryDriverId,
   DeliveryRouteStatusEnum deliveryRouteStatus,
   LocalDateTime createdAt,
   Long createdBy,
   LocalDateTime updatedAt,
   Long updatedBy
) {}
