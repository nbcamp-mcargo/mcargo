package com.mcargo.authservice.presentation.dto.response;

public record UserUpdateResponseDto(
    String message,
    UserInformationDto userInfo
) {
}
