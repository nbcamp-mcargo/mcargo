package com.mcargo.authservice.presentation.dto.response;

import com.mcargo.common.auth.UserRole;

import java.time.LocalDateTime;

public record UserInformationDto(
    // 사용자명 (로그인 시 사용)
    String username,
    // 닉네임 (화면 표시용)
    String nickname,
    // 이메일 주소
    String email,
    // 권한
    UserRole role,
    // 계정 생성일
    LocalDateTime createdAt,
    // 마지막 수정일
    LocalDateTime updatedAt
) {

}

/// / 사용자 ID
//Long userId