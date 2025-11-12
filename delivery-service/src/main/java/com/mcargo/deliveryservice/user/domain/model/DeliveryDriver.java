package com.mcargo.deliveryservice.user.domain.model;

import com.mcargo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_delivery_driver")
public class DeliveryDriver extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="delivery_driver_id", updatable = false, nullable = false)
    private UUID deliveryDriverId;

    // TODO : 사용자 테이블과 연관관계 추가
    private Long userId;

    private UUID hubId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryDriverType deliveryDriverType;

    @Column(nullable = false)
    private int deliveryDriverNumber;
    private boolean isAvailable;


    public static DeliveryDriver createDeliveryDriver(Long userId,
                                                      DeliveryDriverType deliveryDriverType,
                                                      int deliveryDriverNumber) {

        DeliveryDriver deliveryDriver = new DeliveryDriver();
        deliveryDriver.userId = userId;
        deliveryDriver.deliveryDriverType = deliveryDriverType;
        deliveryDriver.deliveryDriverNumber = deliveryDriverNumber;

        return deliveryDriver;
    }
}
