package com.mcargo.authservice.presentation.dto.response;

import com.mcargo.authservice.domain.entity.UserStatus;

public record UserSignUpResponseDto(
    String username,
    String nickname,
    String email,
    UserStatus status
) {

}
