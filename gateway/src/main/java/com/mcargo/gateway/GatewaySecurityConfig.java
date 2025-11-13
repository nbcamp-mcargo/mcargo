//package com.mcargo.gateway;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//
//@Configuration
//@RequiredArgsConstructor
//public class GatewaySecurityConfig {
//    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
//
//    /*
//     * Gateway 전용 SecurityWebFilterChain
//     */
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        return http
//            // CSRF는 WebFlux + JWT 사용할 때 불필요
//            .csrf(ServerHttpSecurity.CsrfSpec::disable)
//            // HTTP Basic Auth 제거 (401 + 로그인 팝업 방지)
//            .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
//            // Form Login 제거
//            .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
//            // 로그아웃 제거
//            .logout(ServerHttpSecurity.LogoutSpec::disable)
//            // 예외 처리: 인증/인가 실패 시 401 리턴
/// /            .exceptionHandling(exception -> exception
/// /                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
/// /                .accessDeniedHandler((exchange, denied) -> {
/// /                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
/// /                    return exchange.getResponse().setComplete();
/// /                })
/// /            )
//            // HTTP 요청에 대한 인증/인가 규칙 설정
////            .authorizeExchange(authorize -> authorize
////                // 인증 필요 없는 화이트리스트
////                .pathMatchers(
////                    "/auths/login",
////                    "/auths/signUp",
////                    "/auths/refresh"
////                ).permitAll()
////                // Options 요청 허용 (CORS preflight)
////                .pathMatchers(HttpMethod.OPTIONS).permitAll()
////                // 나머지 요청은 JWT 인증 필요
////                .anyExchange().authenticated()
////            )
//            // 모든 요청을 permitAll() - JWT 인증은 GlobalFilter가 처리
//            .authorizeExchange(ex -> ex.anyExchange().permitAll())
//            .build();
//    }
//}
