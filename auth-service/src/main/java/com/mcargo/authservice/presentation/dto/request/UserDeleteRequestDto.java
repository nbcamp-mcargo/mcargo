package com.mcargo.authservice.presentation.dto.request;

import jakarta.validation.constraints.NotBlank;

public record UserDeleteRequestDto(
    @NotBlank(message = "비밀번호는 필수입니다.")
    String password
) {
    
}
