package com.mcargo.hubservice.presentation.dto;

public record NavigateHubRouteResponse(
        int sequence,               // 순서

        String from,                // 출발지
        String to,                  // 도착지
        String fromAddress,         // 출발지 주소
        String toAddress,           // 도착지 주소

        Integer deliveryDriverNumber, // 배송담당자 번호
        String predicted_distance,  // 예상 거리
        String predicted_time       // 예상 시간
) {}
