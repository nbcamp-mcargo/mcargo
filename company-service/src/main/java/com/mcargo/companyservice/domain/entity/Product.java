package com.mcargo.companyservice.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.mcargo.common.entity.BaseEntity;
import com.mcargo.companyservice.presentation.dto.request.ProductUpdateRequest;
import com.mcargo.companyservice.presentation.dto.response.ProductCreateResponse;
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
public class Product extends BaseEntity {

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

    public ProductReadResponse toReadResponse(Product product) {
        return new ProductReadResponse(
                this.id,
                this.name,
                this.price,
                this.description,
                this.isSale,
                this.hubId
        );
    }

    public void updateEntity(ProductUpdateRequest request) {
        this.name = request.name();
        this.price = request.price();
        this.description = request.description();
        this.isSale = request.isSale();
        this.hubId = request.hubId();
    }

    public ProductCreateResponse toCreateResponse() {
        return new ProductCreateResponse(
                this.name,
                this.price,
                this.description,
                this.isSale,
                this.hubId
        );
    }
}
