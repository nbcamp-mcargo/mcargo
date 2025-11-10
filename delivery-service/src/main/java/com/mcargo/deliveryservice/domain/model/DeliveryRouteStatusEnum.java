package com.mcargo.deliveryservice.domain.model;

public enum DeliveryRouteStatusEnum{
    ACCEPTED("배송 수락"),
    WAITING_AT_HUB("허브 대기"),
    IN_TRANSIT("허브 이동중"),
    ARRIVE_AT_DESTINATION_HUB("목적지 허브 도착"),
    COMPLETE("배송 완료"),
    CANCELED("배송 취소");

    private final String description;

    DeliveryRouteStatusEnum(String description) {
        this.description = description;
    }
}
