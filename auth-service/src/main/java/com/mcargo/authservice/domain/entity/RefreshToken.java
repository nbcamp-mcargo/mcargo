package com.mcargo.authservice.domain.entity;

import com.mcargo.common.entity.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "p_token")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RefreshToken extends BaseEntity {

    // 리프레시 토큰 고유 ID
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private Long refreshTokenId;

    // 실제 JWT 리프레시 토큰 문자열
    @Column(name = "token", nullable = false, length = 500)
    private String token;

    // 토큰 소유 사용자 (N:1관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 토큰 만료 시간
    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    /**
     * 리프레시 토큰 생성자 새로운 리프레시 토큰 생성할 때 사용
     *
     * @param token     JWT 리프레시 토큰 문자열
     * @param user      토큰 소유 사용자
     * @param expiresAt 토큰 만료 시간
     */
    public RefreshToken(String token, User user, LocalDateTime expiresAt) {
        this.token = token;
        this.user = user;
        this.expiresAt = expiresAt;
    }

    /**
     * 토큰 만료 여부 확인. 현재 시간과 만료 시간 비교해 만료 여부 판단
     *
     * @return 만료되었으면 true, 아니면 false
     */
    public boolean isExpired() {
        return LocalDateTime.now().isAfter(this.expiresAt);
    }
}
