package com.mcargo.orderservice.presentation.dto;

public record AiMessageRequest(
        String userEmail,
        String aiRequestMessage
) {
}
