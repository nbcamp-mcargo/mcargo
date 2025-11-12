package com.mcargo.companyservice.presentation.dto.response;

public record CompanyCreateResponse (
        String name,
        String companyType,
        String address,
        Double latitude,
        Double longitude
) {
}
