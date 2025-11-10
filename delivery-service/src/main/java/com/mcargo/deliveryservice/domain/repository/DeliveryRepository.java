package com.mcargo.deliveryservice.domain.repository;

import com.mcargo.deliveryservice.application.dto.DeliverySearchParam;
import com.mcargo.deliveryservice.domain.model.Delivery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRepository {
    Delivery save(Delivery delivery);
    Optional<Delivery> findById(UUID deliveryId);
    boolean existsByOrderIdAndDeletedAtIsNull(UUID orderId);
    Page<Delivery> findAllByDeletedAtIsNull(Pageable pageable);
    Page<Delivery> searchDeliveries(DeliverySearchParam deliverySearchParam, Pageable pageable);
}
