package com.mcargo.deliveryservice.infrastructure.repository;

import com.mcargo.deliveryservice.application.dto.DeliveryRouteSearchParam;
import com.mcargo.deliveryservice.domain.model.Delivery;
import com.mcargo.deliveryservice.domain.model.DeliveryRoute;
import com.mcargo.deliveryservice.domain.repository.DeliveryRouteReposotory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeliveryRouteJpaRepositoryAdaptor implements DeliveryRouteReposotory {
    private final DeliveryRouteJpaRepository deliveryRouteJpaRepository;


    @Override
    public DeliveryRoute save(DeliveryRoute deliveryRoute) {
        return deliveryRouteJpaRepository.save(deliveryRoute);
    }

    @Override
    public Optional<DeliveryRoute> findById(UUID deliveryRouteId) {
        return deliveryRouteJpaRepository.findById(deliveryRouteId);
    }

    @Override
    public Page<DeliveryRoute> findAllByDeletedAtIsNull(Pageable pageable) {
        return deliveryRouteJpaRepository.findAllByDeletedAtIsNull(pageable);
    }

    @Override
    public Page<DeliveryRoute> searchDeliveries(DeliveryRouteSearchParam deliveryRouteSearchParam, Pageable pageable) {
        Page<DeliveryRoute> deliveryPage = deliveryRouteJpaRepository.searchDeliveries(
                deliveryRouteSearchParam.deliveryRouteId(),
                deliveryRouteSearchParam.deliveryId(),
                deliveryRouteSearchParam.fromHubId(),
                deliveryRouteSearchParam.toHubId(),
                deliveryRouteSearchParam.deliveryRouteStatus(),
                pageable);
        return deliveryPage;
    }
}
