package com.mcargo.hubservice.presentation.dto;

import com.mcargo.hubservice.domain.entity.HubProductStatus;

public record UpdateHubProductRequest(
        HubProductStatus hubProductStatus,
        Integer stock

) {
}
