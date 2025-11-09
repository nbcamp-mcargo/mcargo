package com.mcargo.deliveryservice.presentation.response;

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
   LocalDateTime createdAt,
   Long createdBy,
   LocalDateTime updatedAt,
   Long updatedBy
) {}
