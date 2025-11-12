package com.mcargo.companyservice.presentation.dto.request;

import com.mcargo.companyservice.domain.entity.Company;
import com.mcargo.companyservice.domain.entity.CompanyType;

public record CompanyCreateRequest(
    String name,
    CompanyType type,
    String address,
    Double latitude,
    Double longitude
) {
    public Company toEntity() {
        return Company.builder()
                .name(this.name)
                .type(this.type)
                .address(this.address)
                .latitude(this.latitude)
                .longitude(this.longitude)
                .build();
    }
}
