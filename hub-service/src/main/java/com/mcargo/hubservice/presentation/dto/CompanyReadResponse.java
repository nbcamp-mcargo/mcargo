package com.mcargo.hubservice.presentation.dto;

public record CompanyReadResponse (
        String name,
        String companyType,
        String address,
        Double latitude,
        Double longitude
) { }