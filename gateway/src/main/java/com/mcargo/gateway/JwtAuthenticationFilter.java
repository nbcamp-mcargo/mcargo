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

/**
 * 이 클래스는 Spring Cloud Gateway 전용 JWT 인증 필터라서 WebFilter를 구현할 이유가 없습니다.
 */
@Slf4j
@Component
public class JwtAuthenticationFilter implements GlobalFilter {
    @Value("${service.jwt.secret-key}")
    private String secretKeyProp;

    private SecretKey secretKey;
    // 필요한 변수
    private String token;
    private Claims claims;
    private ServerHttpRequest newRequest;
    private ServerWebExchange applyUsers;

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

        /**
         * 1. HTTP Request header -> Authroziation Header 존재 여부 확인
         * 2. 해당 Authorization Header가 Bearer Token 인지 확인
         * 3. Bearer Token이 JWT인지
         * 4. 우리가 발급한 JWT 인지
         * 5. JWT에서 Principal (UserId, UserRole) 추출
         * 6. Http Rquest Header에 X-USER-ID, X-USER-ROLE 항목 설정 후 라우팅
         *
         * 7. 각 마이크로 서비스는 @CurrentUser(UserContext(X-USER-ID, X-USRE-ROLE)) -> CreatedBy, UpdatedBy
         */
        //
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
        // 1 HTTP Request header -> Authroziation Header 존재 여부 확인
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("authHeader: {} ", authHeader);
        // 2 해당 Authorization Header가 Bearer Token 인지 확인
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
        }
        log.info("token: {} ", token);
        if (token == null) {
            return unauthorized(exchange, "인가 헤더에 토큰이 없습니다.");
        }
        // 3 BearerToken이 JWT인지 확인
        // 4 우리가 발급한 JWT인지 확인
        try {
            // JWT 검증
            claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        } catch (Exception e) {
            log.warn("JWT token validation failed: {}", e.getMessage());
        }
        if (claims == null) {
            return unauthorized(exchange, "유효 JWT 토큰이 없습니다.");
        }
        // 5 JWT에서 Principal (UserId, UserRole) 추출
        // 6 Http Rquest Header에 X-USER-ID, X-USER-ROLE 항목 설정 후 라우팅
        ServerWebExchange mutableExchange = applyUserHeaders(exchange, claims);
        // 토큰 유효하면 그대로 다음 서비스로 전달
        return chain.filter(mutableExchange);
    }


    /**
     * JWT 유효성 검사
     *
     * @param token
     * @return
     */
    private Claims ValidateToken(String token) {
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