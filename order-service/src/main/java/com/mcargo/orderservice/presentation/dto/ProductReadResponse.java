package com.mcargo.orderservice.presentation.dto;

public record ProductReadResponse(
        String id,
        String name,
        Integer price,
        String description,
        Boolean isSale,
        String hubId
) { }