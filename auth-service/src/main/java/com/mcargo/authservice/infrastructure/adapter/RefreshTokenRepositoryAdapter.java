package com.mcargo.authservice.infrastructure.adapter;

import com.mcargo.authservice.domain.entity.RefreshToken;
import com.mcargo.authservice.domain.entity.User;
import com.mcargo.authservice.domain.repository.RefreshTokenRepository;
import com.mcargo.authservice.infrastructure.repository.RefreshTokenJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepositoryAdapter implements RefreshTokenRepository {

    // 필드 선언
    private final RefreshTokenJpaRepository refreshTokenJpaRepository;

    // 기본 CRUD
    @Override
    public RefreshToken save(RefreshToken refreshToken) {
        return refreshTokenJpaRepository.save(refreshToken);
    }

    @Override
    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenJpaRepository.findByToken(token);
    }

    @Override
    public void delete(RefreshToken refreshToken) {
        refreshTokenJpaRepository.delete(refreshToken);
    }

    // 활성 토큰 조회 (삭제되지 않은)
    @Override
    public Optional<RefreshToken> findByActiveByToken(String token) {
        return refreshTokenJpaRepository.findByTokenAndDeletedAtIsNull(token);
    }

    // 사용자별 토큰 조회
    @Override
    public Optional<RefreshToken> findByUser(User user) {
        return refreshTokenJpaRepository.findByUser(user);
    }

    // 토큰 삭제
    @Override
    public void deleteByUser(User user) {
        refreshTokenJpaRepository.deleteByUser(user);
    }
}