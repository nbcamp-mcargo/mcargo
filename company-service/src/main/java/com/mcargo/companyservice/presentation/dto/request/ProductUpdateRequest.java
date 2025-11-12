package com.mcargo.companyservice.presentation.dto.request;

public record ProductUpdateRequest(
        String companyId,
        String productId,
        String name,
        Integer price,
        String description,
        Boolean isSale,
        String hubId
) { }
