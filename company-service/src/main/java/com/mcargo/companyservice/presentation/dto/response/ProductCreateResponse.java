package com.mcargo.companyservice.presentation.dto.response;

public record ProductCreateResponse(
        String name,
        Integer price,
        String description,
        Boolean isSale,
        String hubId
) {
}
