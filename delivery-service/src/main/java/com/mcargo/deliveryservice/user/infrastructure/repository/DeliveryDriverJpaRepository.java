package com.mcargo.deliveryservice.user.infrastructure.repository;

import com.mcargo.deliveryservice.user.domain.model.DeliveryDriver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DeliveryDriverJpaRepository extends JpaRepository<DeliveryDriver, UUID> {
}
