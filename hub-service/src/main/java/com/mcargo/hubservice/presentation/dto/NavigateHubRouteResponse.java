package com.mcargo.hubservice.presentation.dto;

import com.mcargo.hubservice.application.dto.GetDistanceAndDurationResponse;
import com.mcargo.hubservice.domain.entity.Hub;
import com.mcargo.hubservice.domain.entity.HubRouteSequence;

public record NavigateHubRouteResponse(
    int sequence,               // 순서

    String from,                // 출발지
    String to,                  // 도착지
    String fromAddress,         // 출발지 주소
    String toAddress,           // 도착지 주소

    Integer deliveryDriverNumber, // 배송담당자 번호
    String predicted_distance,  // 예상 거리
    String predicted_time       // 예상 시간
) {
    public static NavigateHubRouteResponse of(
        HubRouteSequence seq,
        Hub seqFromHub,
        Hub seqToHub,
        int hubDriverNumber,
        GetDistanceAndDurationResponse hubPredictData
    ) {
        return new NavigateHubRouteResponse(
            seq.getSequence(),

            seqFromHub.getName(),
            seqToHub.getName(),
            seqFromHub.getAddress(),
            seqToHub.getAddress(),

            hubDriverNumber,
            hubPredictData.distanceText(),
            hubPredictData.durationText());
    }

}
