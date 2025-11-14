package com.mcargo.authservice.presentation.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UserUpdateRequestDto(
    // 최소 4자 이상, 10자 이하이며 알파벳 소문자(a~z), 숫자(0~9)`
    @NotBlank(message = "사용자명은 필수입니다.")
    @Size(min = 4, max = 10, message = "사용자명은 4-10자 이내여야 합니다.")
    @Pattern(regexp = "^[a-z0-9]+$", message = "사용자명은 영소문자와 숫자만 사용 가능합니다.")
    String username,
    @NotBlank(message = "닉네임은 필수입니다.")
    @Size(min = 2, max = 10, message = "닉네임은 2-10자 이내여야 합니다.")
    String nickname,
    // 현재 비밀번호 (비밀번호 변경할 때 필수)
    @Nullable
    String curPassword,
    // 최소 8자 이상, 15자 이하이며 알파벳 대소문자(a~z, A~Z), 숫자(0~9), 특수문자
    @Nullable
    @Size(min = 8, max = 15, message = "비밀번호는 8-15자 이내여야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!@#$%^&*])[a-zA-z\\d!@#$%^&*]+$",
        message = "비밀번호는 영대소문자, 숫자, 특수문자를 모두 포함해야 합니다.")
    String newPassword
) {

}
