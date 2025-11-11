package com.mcargo.authservice;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.annotation.PostConstruct;
import java.util.Date;
import javax.crypto.SecretKey;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtUtil {

    // Http Authorization 헤더 이름
    public static final String AUTHORIZATION_HEADER = "Authorization";
    // Bearer 토큰 접두사
    public static final String BEARER_PREFIX = "Bearer ";
    // 값 선언
    @Value("${spring.application.name}")
    private String issuer;

    @Value("${service.jwt.secret-key}")
    private String secretKeyProp;

    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;

    @Value("${service.jwt.refresh-expiration}")
    private Long refreshExpiration;
    // JWT 서명에 사용할 SecretKey 객체
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKeyProp));
    }

    // 엑세스 토큰 생성
    public String createAccessToken(Long userId, String email, String role) {
        System.out.println("secretKey = " + secretKey);
        System.out.println("email = " + email);
        System.out.println("role = " + role);
        return Jwts.builder()
            .subject(String.valueOf(userId))
            .claim("email", email)
            .claim("role", role)
            .claim("type", "access")
            .issuer(issuer)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + accessExpiration))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    // 리프레시 토큰 생성
    public String createRefreshToken(Long userId) {
        System.out.println("userId = " + userId);
        return Jwts.builder()
            .subject(String.valueOf(userId))
            .claim("type", "refresh")
            .issuer(issuer)
            .issuedAt(new Date(System.currentTimeMillis()))
            .expiration(new Date(System.currentTimeMillis() + refreshExpiration))
            .signWith(secretKey, SignatureAlgorithm.HS256)
            .compact();
    }

    // JWT 토큰에서 payload 추출
    public Claims getClaimsFromToken(String token) {
        try {
            return Jwts.parser()
                // 비밀키로 서명 검증
                .verifyWith(secretKey)
                .build()
                // 서명된 토큰 파싱
                .parseSignedClaims(token)
                // 페이로드 반환
                .getPayload();
        } catch (ExpiredJwtException e) {
            log.warn("만료된 JWT 토큰입니다.");
            throw e;
        } catch (UnsupportedJwtException e) {
            log.warn("지원되지 않는 JWT 토큰입니다.");
            throw e;
        } catch (MalformedJwtException e) {
            log.warn("잘못된 형식의 JWT 토큰입니다.");
            throw e;
        } catch (SecurityException | IllegalArgumentException e) {
            log.warn("잘못된 JWT 토큰입니다.");
            throw e;
        }
    }

    public Long getUserIdFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return Long.parseLong(claims.getSubject());
    }

    public String getRoleFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        return claims.get("role", String.class);
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }

    public Long getAccessTokenExpiration() {
        return accessExpiration / 1000;
    }

    public Long getRefreshTokenExpiration() {
        return refreshExpiration / 1000;
    }
}
