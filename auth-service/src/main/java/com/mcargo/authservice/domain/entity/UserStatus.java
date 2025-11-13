package com.mcargo.authservice.domain.entity;

public enum UserStatus {
    PENDING("승인 대기"),
    APPROVED("승인"),
    REJECTED("거절");

    private final String description;

    UserStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
