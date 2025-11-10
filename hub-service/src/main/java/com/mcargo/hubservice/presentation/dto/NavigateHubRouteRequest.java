package com.mcargo.hubservice.presentation.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NavigateHubRouteRequest(
        @NotNull(message = "출발허브id를 입력하세요.")
        UUID fromHubId,

        @NotNull(message = "도착허브id를 입력하세요.")
        UUID toHubId,

        @NotBlank(message = "수령 업체 주소를 입력하세요.")
        String destinationAddress
) {
}
