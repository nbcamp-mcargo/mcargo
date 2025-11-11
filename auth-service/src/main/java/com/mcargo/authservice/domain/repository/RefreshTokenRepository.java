package com.mcargo.authservice.domain.repository;

import com.mcargo.authservice.domain.entity.RefreshToken;
import com.mcargo.authservice.domain.entity.User;
import java.util.Optional;

public interface RefreshTokenRepository {

    // 기본 CRUD
    RefreshToken save(RefreshToken refreshToken);

    Optional<RefreshToken> findByToken(String token);

    void delete(RefreshToken refreshToken);

    // 활성 토큰 조회 (삭제되지 않은)
    Optional<RefreshToken> findByActiveByToken(String token);

    // 사용자별 토큰 조회
    Optional<RefreshToken> findByUser(User user);

    // 토큰 삭제
    void deleteByUser(User user);
}
