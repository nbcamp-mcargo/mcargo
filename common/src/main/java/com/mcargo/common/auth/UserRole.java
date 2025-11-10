package com.mcargo.common.auth;

public enum UserRole {

    MASTER("마스터 관리자"),
    HUB_MANAGER("허브 관리자"),
    DELIVERY_MANAGER("배송 담당자"),
    COMPANY_MANAGER("업체 담당자");

    private final String description;

    UserRole(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
