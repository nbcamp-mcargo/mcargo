package com.mcargo.hubservice.presentation.dto;

import java.util.UUID;

public record NavigateHubRouteResponse(
        int sequence,               // 순서

        UUID seqStartHubId,         // 시퀀스 별 출발허브id
        UUID seqDestHubId,         // 시퀀스 별 도착허브id

        UUID deliveryDriverId,      // 배송담당자 id
        long predictedDistance,  // 예상 거리
        long predictedTime       // 예상 시간
) {}
