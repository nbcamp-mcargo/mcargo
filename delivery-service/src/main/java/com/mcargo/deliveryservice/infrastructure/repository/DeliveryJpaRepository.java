package com.mcargo.deliveryservice.infrastructure.repository;

import com.mcargo.deliveryservice.application.dto.DeliverySearchParam;
import com.mcargo.deliveryservice.domain.model.Delivery;
import com.mcargo.deliveryservice.domain.model.DeliveryStatusEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface DeliveryJpaRepository extends JpaRepository<Delivery, UUID> {
    boolean existsByOrderIdAndDeletedAtIsNull(UUID orderId);
    Page<Delivery> findAllByDeletedAtIsNull(Pageable pageable);

    @Query("""
        SELECT d
          FROM Delivery d
         WHERE d.deletedAt is null
           AND (:orderId is null or d.orderId = :orderId)
           AND (:receiverUserId is null or d.receiverUserId = :receiverUserId)
           AND (:deliveryStatus is null or d.deliveryStatus = :deliveryStatus)
    """)
    Page<Delivery> searchDeliveries(UUID orderId, Long receiverUserId, DeliveryStatusEnum deliveryStatus, Pageable pageable);
}
