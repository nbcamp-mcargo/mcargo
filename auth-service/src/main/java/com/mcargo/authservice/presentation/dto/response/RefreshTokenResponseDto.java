package com.mcargo.authservice.presentation.dto.response;

public record RefreshTokenResponseDto(
    // 토큰 갱신 성공 메시지
    String message,
    // 새로 발급된 JWT 액세스 토큰
    String accessToken,
    // 새로 발급된 JWT 리프레시 토큰
    String refreshToken,
    // 토큰 타입
    String tokenType,
    // 액세스 토큰 만료 시간
    long expiresIn
) {

}
