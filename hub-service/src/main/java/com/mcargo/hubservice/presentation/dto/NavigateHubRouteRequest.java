package com.mcargo.hubservice.presentation.dto;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record NavigateHubRouteRequest(
        @NotNull(message = "출발허브id를 입력하세요.")
        UUID fromHubId,

        @NotNull(message = "도착허브id를 입력하세요.")
        UUID toHubId,

        @NotNull(message = "수령 업체id를 입력하세요.")
        UUID toCompanyId
) {
}
