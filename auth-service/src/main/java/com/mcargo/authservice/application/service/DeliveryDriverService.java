package com.mcargo.authservice.application.service;

import com.mcargo.authservice.domain.entity.DeliveryDriver;
import com.mcargo.authservice.domain.entity.DeliveryDriverNumberSeq;
import com.mcargo.authservice.domain.entity.DeliveryDriverType;
import com.mcargo.authservice.infrastructure.repository.DeliveryDriverJpaRepository;
import com.mcargo.authservice.infrastructure.repository.DeliveryDriverNumberSeqJpaRepository;
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

    public UUID getNextDriver(UUID hubId) {
        DeliveryDriver driver = null;

        if (hubId == null) { // 허브 드라이버 가져옴
            driver = deliveryDriverJpaRepository.findDeliveryDriver(DeliveryDriverType.HUB_DRIVER, null)
                    .orElseThrow();
        } else {
            driver = deliveryDriverJpaRepository.findDeliveryDriver(DeliveryDriverType.COMPANY_DRIVER, hubId)
                    .orElseThrow();
        }

        return driver.getDeliveryDriverId();
    }

    /**
     * 배송 담당자 생성을 위한 시퀀스 생성
     * @param hubId
     */
    public void createDeliveryDriverSeq(UUID hubId) {
        DeliveryDriverNumberSeq driverSeq = null;

        if(hubId == null) {
            driverSeq = DeliveryDriverNumberSeq.createHubDriver();
        } else {
            driverSeq = DeliveryDriverNumberSeq.createCompanyDriver(hubId);
        }

        deliveryDriverNumberSeqJpaRepository.save(driverSeq);
    }
}
