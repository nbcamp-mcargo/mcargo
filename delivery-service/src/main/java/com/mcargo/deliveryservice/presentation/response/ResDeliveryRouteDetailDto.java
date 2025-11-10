package com.mcargo.deliveryservice.presentation.response;

import com.mcargo.deliveryservice.domain.model.DeliveryRouteStatusEnum;

import java.time.LocalDateTime;
import java.util.UUID;

public record ResDeliveryRouteDetailDto(
   UUID deliveryRouteId,
   UUID deliveryId,
   int sequence,
   UUID fromHubId,
   UUID toHubId,
   Long predictedDistance,
   Long predictedTime,
   Long actualDistance,
   LocalDateTime actualTime,
   UUID deliveryDriverId,
   DeliveryRouteStatusEnum deliveryRouteStatus,
   LocalDateTime createdAt,
   Long createdBy,
   LocalDateTime updatedAt,
   Long updatedBy
) {}
