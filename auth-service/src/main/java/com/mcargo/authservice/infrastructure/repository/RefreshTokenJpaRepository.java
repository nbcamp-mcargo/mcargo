package com.mcargo.authservice.infrastructure.repository;

import com.mcargo.authservice.domain.entity.RefreshToken;
import com.mcargo.authservice.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenJpaRepository extends JpaRepository<RefreshToken, Long> {

    Optional<RefreshToken> findByToken(String token);

    // 활성 토큰 조회 (삭제되지 않은)
    Optional<RefreshToken> findByTokenAndDeletedAtIsNull(String token);

    // 사용자별 토큰 조회
    Optional<RefreshToken> findByUser(User user);

    // 토큰 삭제
    void deleteByUser(User user);
}
