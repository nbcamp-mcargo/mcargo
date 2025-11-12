package com.mcargo.companyservice.presentation.dto.request;

import com.mcargo.companyservice.domain.entity.Company;
import com.mcargo.companyservice.domain.entity.Product;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductCreateRequest(
        @NotBlank String companyId,
        @NotBlank String name,
        @NotNull Integer price,
        String description,
        Boolean isSale,
        String hubId
) {
    public Product toEntity(Company company) {
        return Product.builder()
                .company(company)
                .name(this.name)
                .price(this.price)
                .description(this.description)
                .isSale(this.isSale)
                .hubId(this.hubId)
                .build();
    }
}
