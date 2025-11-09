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
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryRoute extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="delivery_route_id", updatable = false, nullable = false)
    private UUID deliveryRouteId;

    @Column(nullable = false)
    private int sequence;

    @Column(nullable = false)
    private UUID fromHubId;

    @Column(nullable = false)
    private UUID toHubId;

    @Column(nullable = false)
    private int estimatedDistance;

    @Column(nullable = false)
    private LocalDateTime estimatedTime;

    private int actualDistance;

    private LocalDateTime actualTime;

    @Column(nullable = false)
    private UUID deliveryDriverId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id", nullable = false)
    private Delivery delivery;

}
