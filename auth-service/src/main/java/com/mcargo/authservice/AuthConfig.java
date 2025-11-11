package com.mcargo.authservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class AuthConfig {

    private final LoggingFilter loggingFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.debug("Security Filter Chain");
        http
            // JWT 토큰 기반 인증에서는 CSRF 보호 불필요
            .csrf(csrf -> csrf.disable())
//            .addFilterBefore(loggingFilter, UsernamePasswordAuthenticationFilter.class)
            // HTTP 요청에 대한 인증/인가 규칙 설정
            .authorizeHttpRequests(authorize -> authorize
                // swagger 접근 가능
                .requestMatchers(
                    "api/v3/api-docs/**",
                    "/swagger-ui.html",
                    "/swagger-ui/**").permitAll()
                // 인증 없이 접근 가능한 엔드포인트 (회원가입, 로그인, 토큰 갱신)
                .requestMatchers(
                    "/auths/signUp",
                    "/auths/login",
                    "/auths/refresh").permitAll()
                // 사용자 목록 조회는 MASTER 권한만 접근 가능
                .requestMatchers(
                    "/auths",
                    "/auths/").hasAnyRole("MASTER")
                // 개별 사용자 관련 기능은 인증된 사용자 모두 접근 가능
                .requestMatchers(HttpMethod.GET, "/auths/me").authenticated()
                .requestMatchers(HttpMethod.PUT, "/auths/me").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/auths/me").authenticated()
                .requestMatchers(HttpMethod.POST, "/auths/logout").authenticated()
                .anyRequest().authenticated()
            )
            // JWT 토큰 기반 인증에서는 서버 세션을 사용하지 않음
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .addFilterAfter(loggingFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
