package com.mcargo.deliveryservice.user.infrastructure.repository;

import com.mcargo.deliveryservice.user.domain.model.DeliveryDriverNumberSeq;
import com.mcargo.deliveryservice.user.domain.model.DeliveryDriverType;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

import java.util.Optional;
import java.util.UUID;

public interface DeliveryDriverNumberSeqJpaRepository extends JpaRepository<DeliveryDriverNumberSeq, UUID> {

    // 트랜잭션 처리 중 다른 트랜잭션에서 읽기/쓰기 불가 (FOR UPDATE)
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<DeliveryDriverNumberSeq> findByDeliveryDriverTypeAndHubId(DeliveryDriverType deliveryDriverType, UUID hubId);

}
