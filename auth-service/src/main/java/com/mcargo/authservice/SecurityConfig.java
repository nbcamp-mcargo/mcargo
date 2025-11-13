/// /package com.mcargo.authservice;
/// /
/// /import lombok.RequiredArgsConstructor;
/// /import lombok.extern.slf4j.Slf4j;
/// /import org.springframework.context.annotation.Bean;
/// /import org.springframework.context.annotation.Configuration;
/// /import org.springframework.http.HttpMethod;
/// /import org.springframework.http.HttpStatus;
/// /import org.springframework.security.config.web.server.ServerHttpSecurity;
/// /import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
/// /import org.springframework.security.crypto.password.PasswordEncoder;
/// /import org.springframework.security.web.server.SecurityWebFilterChain;
/// /
/// /@Slf4j
/// /@Configuration
/// /@RequiredArgsConstructor
/// /public class SecurityConfig {
/// /
//@Bean
//public SecurityWebFilterChain securityFilterChain(ServerHttpSecurity http) throws Exception {
//    log.info("Security Filter Chain (WebFlux) initialized");
//    return http
//        .csrf(ServerHttpSecurity.CsrfSpec::disable)
//        .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
//        .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
//        .logout(ServerHttpSecurity.LogoutSpec::disable)
//
//        // HTTP 요청에 대한 인증/인가 규칙 설정
//        .authorizeExchange(authorize -> authorize
//            // swagger 접근 가능
//            .pathMatchers(
//                "/v3/api-docs/**",
//                "/swagger-ui.html",
//                "/swagger-ui/**").permitAll()
//            .pathMatchers(HttpMethod.OPTIONS).permitAll()
//            // 인증 없이 접근 가능한 엔드포인트 (회원가입, 로그인, 토큰 갱신)
//            .pathMatchers(
//                "/auths/signUp",
//                "/auths/login",
//                "/auths/refresh"
//            ).permitAll()
//            // 사용자 목록 조회는 MASTER 권한만 접근 가능
//            .pathMatchers(
//                "/auths",
//                "/auths/").hasAnyRole("MASTER")
//            // 개별 사용자 관련 기능은 인증된 사용자 모두 접근 가능
//            .pathMatchers(HttpMethod.GET, "/auths/me").authenticated()
//            .pathMatchers(HttpMethod.PUT, "/auths/me").authenticated()
//            .pathMatchers(HttpMethod.DELETE, "/auths/me").authenticated()
//            .pathMatchers(HttpMethod.POST, "/auths/logout").authenticated()
//            .anyExchange().authenticated()
//        )
//        .build();
//}
////
////    @Bean
////    PasswordEncoder passwordEncoder() {
////        return new BCryptPasswordEncoder();
////    }
////}
