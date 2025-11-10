package com.mcargo.deliveryservice.domain.repository;

import com.mcargo.deliveryservice.application.dto.DeliveryRouteSearchParam;
import com.mcargo.deliveryservice.domain.model.Delivery;
import com.mcargo.deliveryservice.domain.model.DeliveryRoute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryRouteReposotory {
    DeliveryRoute save(DeliveryRoute deliveryRoute);
    Optional<DeliveryRoute> findById(UUID deliveryRouteId);
    Page<DeliveryRoute> findAllByDeletedAtIsNull(Pageable pageable);
    Page<DeliveryRoute> searchDeliveries(DeliveryRouteSearchParam deliveryRouteSearchParam, Pageable pageable);
}
