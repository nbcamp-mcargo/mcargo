package com.mcargo.companyservice.domain.entity;

import com.mcargo.companyservice.presentation.dto.request.ProductCreateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private Integer price;

    private String description;

    private Boolean isSale;

    private String hubId;

    @Builder(access = AccessLevel.PUBLIC)
    private Product(Company company, Integer price, String description, Boolean isSale, String hubId) {
        this.company = company;
        this.price = price;
        this.description = description;
        this.isSale = isSale;
        this.hubId = hubId;
    }
}
