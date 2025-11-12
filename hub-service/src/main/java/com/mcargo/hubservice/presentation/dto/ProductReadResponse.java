package com.mcargo.hubservice.presentation.dto;

public record ProductReadResponse(
        String id,
        String name,
        Integer price,
        String description,
        Boolean isSale,
        String hubId
) { }