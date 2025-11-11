package com.mcargo.deliveryservice.domain.model;

import com.mcargo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "p_delivery")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Delivery extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="delivery_id", updatable = false, nullable = false)
    private UUID deliveryId;

    @Column(nullable = false)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatusEnum deliveryStatus;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Long receiverUserId;

    @Column(nullable = false)
    private String receiverSlackId;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryRoute> deliveryRoutes = new ArrayList<>();

    public void addRoute(DeliveryRoute route) {
        this.deliveryRoutes.add(route);
        route.addDelivery(this);
    }

    public static Delivery createDelivery(UUID orderId,
                                          String address,
                                          Long receiverUserId,
                                          String receiverSlackId) {
        Delivery delivery = new Delivery();

        delivery.orderId = orderId;
        delivery.deliveryStatus = DeliveryStatusEnum.ACCEPTED;
        delivery.address = address;
        delivery.receiverUserId = receiverUserId;
        delivery.receiverSlackId = receiverSlackId;

        return delivery;
    }

    public void updateDeliveryStatus(DeliveryStatusEnum deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }

    public void cencelDeliveryWithRoutes() {
        this.deliveryStatus = DeliveryStatusEnum.CANCELED;
        // TODO : 사용자 정보 추가

        for(DeliveryRoute route : this.deliveryRoutes) {
            route.cencelDeliveryRoute();
        }
    }

    public void softDeleteWithRoutes() {
        this.deletedAt = LocalDateTime.now();

        for(DeliveryRoute route : this.deliveryRoutes) {
            route.softDelete();
        }
    }
    /**
     * route의 상태에 따라 delivery 상태를 다시 계산
     *
     * 배송 생성 직후 : ACCEPTED
     * 첫 번째 허브 도착 & 출발 전 : WAITING_AT_HUB
     * 허브 이동중 : IN_TRANSIT
     * 최종 허브 도착 : ARRIVE_AT_DESTINATION_HUB
     * 배송 완료 : COMPLETE
     */
    public void refreshStatusByRoutes() {
        if (this.deliveryStatus == DeliveryStatusEnum.CANCELED) {
            return;
        }

        boolean anyInTransit = deliveryRoutes.stream()
                .anyMatch(route -> route.getDeliveryRouteStatus() == DeliveryRouteStatusEnum.IN_TRANSIT);

        boolean lastHubArrived = deliveryRoutes.stream()
                .filter(route -> isLastHubRoute(route.getSequence()))
                .anyMatch(route -> route.getDeliveryRouteStatus() == DeliveryRouteStatusEnum.ARRIVE_AT_DESTINATION_HUB);

        boolean allCompleted = deliveryRoutes.stream()
                .allMatch(route -> route.getDeliveryRouteStatus() == DeliveryRouteStatusEnum.COMPLETE);

        boolean isOutForDelivery = deliveryRoutes.stream()
                .filter(route -> isLastCompanyRoute(route.getSequence()))
                .anyMatch(route -> route.getDeliveryRouteStatus() == DeliveryRouteStatusEnum.IN_TRANSIT);


        if(allCompleted) {
            this.deliveryStatus = DeliveryStatusEnum.COMPLETE;
        } else if(lastHubArrived) {
            this.deliveryStatus = DeliveryStatusEnum.ARRIVE_AT_DESTINATION_HUB;
        } else if(anyInTransit) {
            this.deliveryStatus = DeliveryStatusEnum.IN_TRANSIT;
        } else if(deliveryRoutes.stream()
                .anyMatch(route -> route.getDeliveryRouteStatus() == DeliveryRouteStatusEnum.WAITING_AT_HUB)) {
            this.deliveryStatus = DeliveryStatusEnum.WAITING_AT_HUB;
        } else if(isOutForDelivery) {
            this.deliveryStatus = DeliveryStatusEnum.OUT_FOR_DELIVERY;
        }
    }

    // 마지막 허브인지 판별
    public boolean isLastHubRoute(int sequence) {
        return sequence == maxSequence()-1;
    }

    // 업체 배송 판별
    public boolean isLastCompanyRoute(int sequence) {
        return sequence == maxSequence();
    }

    public int maxSequence() {
        int maxSequence = deliveryRoutes.stream()
                .mapToInt(DeliveryRoute::getSequence)
                .max()
                .orElse(0);
        return maxSequence;
    }
}
