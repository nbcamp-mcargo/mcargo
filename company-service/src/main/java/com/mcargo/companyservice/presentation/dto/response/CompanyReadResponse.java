package com.mcargo.companyservice.presentation.dto.response;

import com.mcargo.companyservice.domain.entity.CompanyType;

public record CompanyReadResponse (
        String name,
        CompanyType type,
        String address,
        Double latitude,
        Double longitude
) { }
