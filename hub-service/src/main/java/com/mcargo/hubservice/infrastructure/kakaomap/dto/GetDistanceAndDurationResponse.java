package com.mcargo.hubservice.infrastructure.kakaomap.dto;

public record GetDistanceAndDurationResponse(
        String distanceText,
        String durationText
) {
}
