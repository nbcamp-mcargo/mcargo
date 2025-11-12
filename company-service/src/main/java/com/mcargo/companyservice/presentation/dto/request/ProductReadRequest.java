package com.mcargo.companyservice.presentation.dto.request;

public record ProductReadRequest(
        String companyId,
        String productId
) { }
