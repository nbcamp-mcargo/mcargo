package com.mcargo.deliveryservice.domain.model;

import com.mcargo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_delivery_route")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRoute extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="delivery_route_id", updatable = false, nullable = false)
    private UUID deliveryRouteId;

//    @Column(nullable = false)
    private int sequence;

//    @Column(nullable = false)
    private UUID fromHubId;

//    @Column(nullable = false)
    private UUID toHubId;

//    @Column(nullable = false)
    private DeliveryRouteStatusEnum deliveryRouteStatus;

//    @Column(nullable = false)
    private Long predictedDistance;

//    @Column(nullable = false)
    private Long predictedTime;

    private Long actualDistance;

    private LocalDateTime actualTime;

//    @Column(nullable = false)
    private UUID deliveryDriverId;

    @Column(name = "delivery_id", insertable = false, updatable = false)
    private UUID deliveryId;    // 읽기 전용 순수 FK 필드

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

    public void addDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public static DeliveryRoute createDeliveryRoute(int sequence,
                                                    UUID fromHubId,
                                                    UUID toHubId,
                                                    Long predictedDistance,
                                                    Long predictedTime,
                                                    UUID deliveryDriverId) {
        DeliveryRoute deliveryRoute = new DeliveryRoute();
        deliveryRoute.sequence = sequence;
        deliveryRoute.fromHubId = fromHubId;
        deliveryRoute.toHubId = toHubId;
        deliveryRoute.deliveryRouteStatus = DeliveryRouteStatusEnum.ACCEPTED;
        deliveryRoute.predictedDistance = predictedDistance;
        deliveryRoute.predictedTime = predictedTime;
        deliveryRoute.deliveryDriverId = deliveryDriverId;

        return deliveryRoute;
    }

    public void cencelDeliveryRoute() {
        this.deliveryRouteStatus = DeliveryRouteStatusEnum.CANCELED;
        // TODO : 사용자 정보 추가
    }

    public void softDelete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void updateStatus(DeliveryRouteStatusEnum deliveryRouteStatus) {
        this.deliveryRouteStatus = deliveryRouteStatus;
        delivery.refreshStatusByRoutes();
    }

    public boolean isLastHubRoute() {
        return delivery.isLastHubRoute(this.sequence);
    }
}
