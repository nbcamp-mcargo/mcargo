package com.mcargo.authservice.presentation.dto.response;

import com.mcargo.common.auth.UserRole;

public record UserInformationDto(
    // 사용자명 (로그인 시 사용)
    String username,
    // 닉네임 (화면 표시용)
    String nickname,
    // 이메일 주소
    String email,
    // 권한
    UserRole role
) {

}

/// / 사용자 ID
//Long userId