package com.mcargo.companyservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mcargo.companyservice.presentation.dto.request.ProductUpdateRequest;
import com.mcargo.companyservice.presentation.dto.response.ProductReadResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private Integer price;

    private String description;

    private Boolean isSale;

    private String hubId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Company company;

    @Builder(access = AccessLevel.PUBLIC)
    private Product(String name, Company company, Integer price, String description, Boolean isSale, String hubId) {
        this.name = name;
        this.company = company;
        this.price = price;
        this.description = description;
        this.isSale = isSale;
        this.hubId = hubId;
    }

    public ProductReadResponse toProductReadResponse(Product product) {
        return new ProductReadResponse(
                this.id,
                this.name,
                this.price,
                this.description,
                this.isSale,
                this.hubId
        );
    }

    public void update(ProductUpdateRequest request) {
        if (request.name() != null) {
            this.name = request.name();
        }

        if (request.price() != null) {
            this.price = request.price();
        }

        if (request.description() != null) {
            this.description = request.description();
        }

        if (request.isSale() != null) {
            this.isSale = request.isSale();
        }

        if (request.hubId() != null) {
            this.hubId = request.hubId();
        }
    }
}
