package com.mcargo.authservice.infrastructure.repository;

import com.mcargo.authservice.domain.entity.DeliveryDriver;
import com.mcargo.authservice.domain.entity.DeliveryDriverType;
import feign.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryDriverJpaRepository extends JpaRepository<DeliveryDriver, UUID> {

    @Query("""
    SELECT d 
    FROM DeliveryDriver d 
    WHERE d.deliveryDriverType = :type 
      AND d.hubId = :hubId
      AND d.isAvailable = true 
    ORDER BY d.deliveryDriverNumber ASC
    LIMIT 1
""")
    Optional<DeliveryDriver> findDeliveryDriver(@Param("type") DeliveryDriverType type, UUID hubId);
}
