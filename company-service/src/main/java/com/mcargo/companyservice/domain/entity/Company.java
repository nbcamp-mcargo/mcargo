package com.mcargo.companyservice.domain.entity;

import com.mcargo.companyservice.presentation.dto.response.CompanyReadResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "p_company")
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CompanyType type;

    private String address;

    private Double latitude;

    private Double longitude;

    @Builder(access = AccessLevel.PUBLIC)
    private Company(String name, String address, Double latitude, Double longitude, CompanyType type) {
        this.name = name;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.type = type;
    }

    public CompanyReadResponse toCompanyReadResponse() {
        return new CompanyReadResponse(
                this.name,
                this.type,
                this.address,
                this.latitude,
                this.longitude
        );
    }
}
