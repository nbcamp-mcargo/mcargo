package com.mcargo.gateway;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.util.List;
import javax.crypto.SecretKey;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class LocalJwtAuthenticationFilter implements GlobalFilter {

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

    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();
        if (exchange.getRequest().getMethod() == HttpMethod.OPTIONS) {
            return chain.filter(exchange);
        }
        log.info("request path before whiteList : {} ", path);
        // 화이트리스트 경로 패스 처리
        if (isWhiteList(path)) {
            return chain.filter(exchange);
        }

        log.info("request path after whiteList : {} ", path);
        // Authorization(인가) 헤더에서 Bearer 토큰 추출
        String token = extractToken(exchange);
        log.info("token: {} ", token);
        if (token == null || !validateToken(token, exchange)) {
            return unauthorized(exchange, "Missing Authentication Header");
        }

        return chain.filter(exchange);

    }

    private boolean isWhiteList(String path) {
        return WHITE_LIST.stream().anyMatch(path::startsWith);
    }

    private String extractToken(ServerWebExchange exchange) {
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("authHeader: {} ", authHeader);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7).trim();
        }
        return null;
    }

    private boolean validateToken(String token, ServerWebExchange exchange) {
        try {
            // JWT 검증
            Jws<Claims> claimsJws = Jwts.parser()
                .verifyWith(secretKey)
                .build().parseSignedClaims(token);
            Claims claims = claimsJws.getPayload();
            exchange.getRequest().mutate()
                .header("X-User-Id", claims.get("userId").toString())
                .header("X-Role", claims.get("role").toString())
                .build();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private Mono<Void> unauthorized(ServerWebExchange exchange, String message) {
        log.info("[JWT AUTH] unauthorized: {} ", message);
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
}