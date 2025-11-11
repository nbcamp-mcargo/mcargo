package com.mcargo.deliveryservice.user.application.service;

import com.mcargo.deliveryservice.user.domain.model.DeliveryDriver;
import com.mcargo.deliveryservice.user.domain.model.DeliveryDriverNumberSeq;
import com.mcargo.deliveryservice.user.domain.model.DeliveryDriverType;
import com.mcargo.deliveryservice.user.infrastructure.repository.DeliveryDriverJpaRepository;
import com.mcargo.deliveryservice.user.infrastructure.repository.DeliveryDriverNumberSeqJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeliveryDriverService {

    DeliveryDriverJpaRepository deliveryDriverJpaRepository;
    DeliveryDriverNumberSeqJpaRepository deliveryDriverNumberSeqJpaRepository;

    /**
     * 배송 담당자 생성
     */
    public void createDeliveryDriver(Long userId, DeliveryDriverType deliveryDriverType) {
        // TODO : 사용자 조회

        UUID hubId = null;
        // 업체 배송 담당자: 허브별 시퀀스 / 허브 배송 담당자 : hubId = null
        if(deliveryDriverType == DeliveryDriverType.COMPANY_DRIVER) {
            hubId = UUID.randomUUID();
        }


        DeliveryDriverNumberSeq seq =  deliveryDriverNumberSeqJpaRepository
                .findByDeliveryDriverTypeAndHubId(deliveryDriverType, hubId)
                .orElseThrow(() -> new RuntimeException("Delivery Driver Number Not Found"));

        Integer nextNumber = seq.getNextNumber();

        seq.increse();

        DeliveryDriver driver = DeliveryDriver.createDeliveryDriver(
                userId,
                deliveryDriverType,
                nextNumber
        );

        deliveryDriverJpaRepository.save(driver);
    }

    public UUID addHubDeliveryDriver(UUID fromHubId) {
        // 해당 허브 사용자 중 배송 가능한 사용자 찾기

        // 해당 사용자 중 우선순위가 가장 높은 사람 return

        return null;
    }
}
