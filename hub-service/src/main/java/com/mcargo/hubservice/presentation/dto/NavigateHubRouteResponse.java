package com.mcargo.hubservice.presentation.dto;

import java.util.UUID;

public record NavigateHubRouteResponse(
        int sequence,               // 순서
        String fromAddress,         // 출발지 주소
        String toAddress,           // 도착지 주소
        UUID deliveryDriverId,       // 배송담당자ID

        double predicted_distance,  // 예상 거리
        int predicted_time          // 예상 시간(분)
) {}
