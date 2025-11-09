package com.mcargo.deliveryservice.presentation.response;

import com.mcargo.deliveryservice.domain.model.DeliveryStatusEnum;

import java.util.UUID;

public class ResDeliveryRouteStatusDto {
    private UUID deliveryRouteId;
    private DeliveryStatusEnum deliveryStatus;

}
