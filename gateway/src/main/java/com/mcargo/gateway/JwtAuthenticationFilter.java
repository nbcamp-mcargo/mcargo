package com.mcargo.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.crypto.SecretKey;
import java.util.List;

@Slf4j
@Component
public class JwtAuthenticationFilter implements GlobalFilter {
    @Value("${service.jwt.secret-key}")
    private String secretKeyProp;

    private SecretKey secretKey;

    // 의존성 주입 후 초기화 수행하는 메서드
    @PostConstruct
    public void init() {
        // AuthService와 동일한 방식으로 키 생성
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKeyProp));
    }

    private static final List<String> WHITE_LIST = List.of(
        "/auths/signUp",
        "/auths/login",
        "/auths/refresh"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        System.out.println("path = " + path);
        // 화이트리스트 경로 패스 처리
        if (WHITE_LIST.stream().anyMatch(path::contains)) {
            System.out.println("whitelist in!!");
            return chain.filter(exchange)
                .doOnSuccess(aVoid -> log.info("Gateway 요청 전달 완료"))
                .doOnError(err -> log.info("Gateway이후 에러 발생" + err.getMessage()));
        }
        // Authorization(인가) 헤더에서 Bearer 토큰 추출
        String token = extractToken(exchange);
        log.info("token: {} ", token);
        if (token == null) {
            return unauthorized(exchange, "인가 헤더에 토큰이 없습니다.");
        }
        Claims claims = validateToken(token);
        System.out.println("claims = " + claims);
        if (claims == null) {
            return unauthorized(exchange, "인가 헤더에 유효 토큰이 없습니다.");
        }
        ServerWebExchange mutableExchange = applyUserHeaders(exchange, claims);
        return chain.filter(mutableExchange)
            .doOnSuccess(v -> log.info("[JWT] {} 요청 성공", path))
            .doOnError(e -> log.error("[JWT] {} 요청 실패: {}", path, e.getMessage()))
            .doFinally(signal -> log.info("[JWT] 요청 종료"));
    }

    /**
     * Authorization 헤더에서 Bearer 토큰 추출
     *
     * @param exchange
     * @return String authHeader
     */
    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("authHeader: {} ", authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    /**
     * JWT 유효성 검사 및 Claim 반환
     *
     * @param token
     * @return
     */
    private Claims validateToken(String token) {
        try {
            // JWT 검증
            return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (Exception e) {
            log.warn("JWT token validation failed: {}", e.getMessage());
            return null;
        }
    }

    /**
     * Claims(Payload)에서 사용자 정보 헤더로 반환하여 전달
     *
     * @param exchange
     * @param claims
     * @return
     */
    private ServerWebExchange applyUserHeaders(ServerWebExchange exchange, Claims claims) {
        ServerHttpRequest newRequest = exchange.getRequest().mutate()
            .header("X-USER-ID", claims.getSubject())
            .header("X-USER-ROLE", claims.get("role", String.class))
            .build();
        return exchange.mutate().request(newRequest).build();
    }

    /**
     * 인증 실패 응답
     *
     * @param exchange
     * @param message
     * @return
     */
    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        log.info("[JWT AUTH] unauthorized: {} ", message);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}