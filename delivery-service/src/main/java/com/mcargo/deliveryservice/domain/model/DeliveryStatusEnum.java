package com.mcargo.deliveryservice.domain.model;

import lombok.Getter;

public enum DeliveryStatusEnum {
    ACCEPTED,
    WAITING_AT_HUB,
    IN_TRANSIT,
    ARRIVE_AT_DESTINATION_HUB,
    OUT_FOR_DELIVERY,
    COMPLETE,
    CANCELED
}
