package com.mcargo.companyservice.domain.entity;

import com.mcargo.common.entity.BaseEntity;
import com.mcargo.companyservice.presentation.dto.request.CompanyUpdateRequest;
import com.mcargo.companyservice.presentation.dto.response.CompanyCreateResponse;
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
public class Company extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CompanyType type;

    private String address;

    private Double latitude;

    private Double longitude;

    public void update(CompanyUpdateRequest request) {
        this.name = request.name();
        this.address = request.address();
        this.latitude = request.latitude();
        this.longitude = request.longitude();
    }

    public CompanyReadResponse toReadResponse() {
        return new CompanyReadResponse(
                this.name,
                this.type.toString(),
                this.address,
                this.latitude,
                this.longitude
        );
    }

    public CompanyCreateResponse toCreateResponse() {
        return new CompanyCreateResponse(
                this.name,
                this.type.toString(),
                this.address,
                this.latitude,
                this.longitude
        );
    }

}
