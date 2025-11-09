package com.mcargo.deliveryservice.presentation.response;

import com.mcargo.deliveryservice.domain.model.DeliveryRouteStatusEnum;
import com.mcargo.deliveryservice.domain.model.DeliveryStatusEnum;

import java.util.UUID;

public record ResDeliveryRouteStatusDto(
        UUID deliveryRouteId,
        DeliveryRouteStatusEnum deliveryStatus
) {}
