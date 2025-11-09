package com.mcargo.deliveryservice.domain.model;

import com.mcargo.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "p_delivery")
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Delivery extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name="delivery_id", updatable = false, nullable = false)
    private UUID deliveryId;

    @Column(nullable = false)
    private UUID orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatusEnum DeliveryStatus;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private Long receiverUserId;

    @Column(nullable = false)
    private String receiverSlackId;

    @OneToMany(mappedBy = "delivery", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DeliveryRoute> deliveryRoutes = new ArrayList<>();
}
