package com.mcargo.companyservice.domain.entity;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CompanyType {
    SUPPLIER_COMPANY("생산 업체", "허브에 물건을 제공해주는 업체"),
    CONSIGNEE_COMPANY("수령 업체", "주문한 물건을 받는 업체");

    private final String type;

    private final String description;
}
