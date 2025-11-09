package com.mcargo.deliveryservice.infrastructure.repository;

import com.mcargo.deliveryservice.application.dto.DeliverySearchParam;
import com.mcargo.deliveryservice.domain.model.Delivery;
import com.mcargo.deliveryservice.domain.repository.DeliveryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class DeliveryJpaRepositoryAdaptor implements DeliveryRepository {
    private final DeliveryJpaRepository deliveryJpaRepository;

    @Override
    public Delivery save(Delivery delivery) {
        return deliveryJpaRepository.save(delivery);
    }

    @Override
    public Optional<Delivery> findById(UUID deliveryId) {
        return deliveryJpaRepository.findById(deliveryId);
    }

    @Override
    public boolean existsByOrderIdAndDeletedAtIsNull(UUID orderId) {
        return deliveryJpaRepository.existsByOrderIdAndDeletedAtIsNull(orderId);
    }

    @Override
    public Page<Delivery> findAllByDeletedAtIsNull(Pageable pageable) {
        return deliveryJpaRepository.findAllByDeletedAtIsNull(pageable);
    }

    @Override
    public Page<Delivery> searchDeliveries(DeliverySearchParam deliverySearchParam, Pageable pageable) {
        return deliveryJpaRepository.searchDeliveries(
                deliverySearchParam.orderId(),
                deliverySearchParam.receiverUserId(),
                deliverySearchParam.deliveryStatus(),
                pageable
        );
    }

}
