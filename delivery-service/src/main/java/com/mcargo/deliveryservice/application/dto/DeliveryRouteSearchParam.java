package com.mcargo.deliveryservice.application.dto;

import com.mcargo.deliveryservice.domain.model.DeliveryRouteStatusEnum;

import java.util.UUID;

public record DeliveryRouteSearchParam(
        UUID deliveryRouteId,
        UUID fromHubId,
        UUID toHubId,
        DeliveryRouteStatusEnum deliveryRouteStatus){}
