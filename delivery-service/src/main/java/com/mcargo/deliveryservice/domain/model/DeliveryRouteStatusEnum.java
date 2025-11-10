package com.mcargo.deliveryservice.domain.model;

public enum DeliveryRouteStatusEnum {
    ACCEPTED,
    WAITING_AT_HUB,
    IN_TRANSIT,
    ARRIVE_AT_DESTINATION_HUB,
    COMPLETE,
    CANCELED
}
