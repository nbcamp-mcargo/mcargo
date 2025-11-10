package com.mcargo.deliveryservice.user.domain.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.UUID;

@Getter
@Entity
@Table(name = "p_delivery_driver_number_seq")
public class DeliveryDriverNumberSeq {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "delivery_driver_seq_id", nullable = false)
    private UUID deliveryDriverSeqId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryDriverType deliveryDriverType;

    // 허브 배송 담당자는 null
    private UUID hubId;

    @Column(nullable = false)
    private Integer nextNumber;

    public void increse() {
        this.nextNumber++;
    }
}

/*
시퀀스 초기 데이터 예시

-- 전체 허브 공용 허브담당자 번호 (1~10)
INSERT INTO delivery_driver_number_seq (delivery_driver_type, hub_id, next_number)
VALUES ('HUB_DRIVER', NULL, 1);

-- A 허브 업체담당자 번호 (1~10)
INSERT INTO delivery_driver_number_seq (delivery_driver_type, hub_id, next_number)
VALUES ('COMPANY_DRIVER', 1, 1);  -- 1번 허브라고 가정

-- B 허브 업체담당자 번호 (1~10)
INSERT INTO delivery_driver_number_seq (delivery_driver_type, hub_id, next_number)
VALUES ('COMPANY_DRIVER', 2, 1);

 */
