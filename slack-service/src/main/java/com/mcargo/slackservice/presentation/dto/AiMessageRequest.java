package com.mcargo.slackservice.presentation.dto;

public record AiMessageRequest(
        String userEmail,
        String aiRequestMessage
) {
}
