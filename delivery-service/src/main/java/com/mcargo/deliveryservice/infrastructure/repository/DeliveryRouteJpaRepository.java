package com.mcargo.deliveryservice.infrastructure.repository;

import com.mcargo.deliveryservice.application.dto.DeliveryRouteSearchParam;
import com.mcargo.deliveryservice.domain.model.Delivery;
import com.mcargo.deliveryservice.domain.model.DeliveryRoute;
import com.mcargo.deliveryservice.domain.model.DeliveryRouteStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface DeliveryRouteJpaRepository extends JpaRepository<DeliveryRoute, UUID> {
    Page<DeliveryRoute> findAllByDeletedAtIsNull(Pageable pageable);

    @Query("""
        SELECT dr
          FROM DeliveryRoute dr
         WHERE dr.deletedAt is null
           AND (:deliveryRouteId is null or dr.deliveryRouteId = :deliveryRouteId)
           AND (:deliveryId is null or dr.deliveryId = :deliveryId)
           AND (:fromHubId is null or dr.fromHubId = :fromHubId)
           AND (:toHubId is null or dr.toHubId = :toHubId)
           AND (:deliveryRouteStatus is null or dr.deliveryRouteStatus = :deliveryRouteStatus)
    """)
    Page<DeliveryRoute> searchDeliveries(UUID deliveryRouteId, UUID deliveryId, UUID fromHubId, UUID toHubId, DeliveryRouteStatusEnum deliveryRouteStatus, Pageable pageable);
}
