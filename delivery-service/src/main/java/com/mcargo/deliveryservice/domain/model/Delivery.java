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
}
