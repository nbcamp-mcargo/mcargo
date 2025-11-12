package com.mcargo.authservice.presentation.dto.response;

import java.time.LocalDateTime;

public record UserDeleteResponseDto(
    // 탈퇴 완료 메시지
    String message,
    // 탈퇴 처리 일시
    LocalDateTime deletedAt,
    // 탈퇴한 사용자 이름
    String username

) {
    
}
