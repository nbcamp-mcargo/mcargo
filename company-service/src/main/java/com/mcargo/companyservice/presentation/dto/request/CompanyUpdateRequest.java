package com.mcargo.companyservice.presentation.dto.request;

public record CompanyUpdateRequest(
    String id,
    String name,
    String address,
    Double latitude,
    Double longitude
) { }
