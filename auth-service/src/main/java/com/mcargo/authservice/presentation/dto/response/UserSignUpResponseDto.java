package com.mcargo.authservice.presentation.dto.response;

public record UserSignUpResponseDto(
    String username,
    String nickname,
    String email
) {

}
