package com.mcargo.companyservice.presentation.dto.response;

public record ProductResponseDto (
    String productId,
    Integer price,
    String description,
    Boolean isSale,
    String hubId
) {}
