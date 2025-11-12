package com.mcargo.authservice.presentation.dto.response;

public record LogoutResponseDto(
    // 로그아웃 성공 메시지
    String message,
    // 로그아웃 처리 시간
    String timestamp
) {

}
