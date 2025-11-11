package com.mcargo.authservice.presentation.dto.response;

public record UserLoginResponseDto(
    // JWT 액세스 토큰 (API 인증)
    String accessToken,
    // JWT 리프레시 토큰 (토큰 갱신)
    String refreshToken,
    // 액세스 토큰 만료 시간 (밀리 초 단위)
    Long expiresIn,
    // 로그인한 사용자 정보
    UserInformationDto userInfo
) {

}

// 토큰 타입
//    String tokenType,