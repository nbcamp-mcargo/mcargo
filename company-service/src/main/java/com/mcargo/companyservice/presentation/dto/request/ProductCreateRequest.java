package com.mcargo.companyservice.presentation.dto.request;

import com.mcargo.companyservice.domain.entity.Company;
import com.mcargo.companyservice.domain.entity.Product;

public record ProductCreateRequest(
        String companyId,
        String name,
        Integer price,
        String description,
        Boolean isSale,
        String hubId
) {
    public Product toEntity(Company company) {
        return Product.builder()
                .company(company)
                .price(this.price)
                .description(this.description)
                .isSale(this.isSale)
                .hubId(this.hubId)
                .build();
    }
}
