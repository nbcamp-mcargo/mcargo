package com.mcargo.companyservice.presentation.dto.response;

public record ProductReadResponse(
        String id,
        String name,
        Integer price,
        String description,
        Boolean isSale,
        String hubId
) {
}
