package com.mcargo.authservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 인증/인가 + 유저
 * - 회원 가입
 * - 로그인
 * - 회원 정보 조회/수정(회원 관리)
 * <p>
 * - Spring Security 의존성 -> PasswordEncoder 사용을 위한 의존성으로 대체
 */
@SpringBootApplication
@EnableJpaAuditing
@ComponentScan(basePackages = {"com.mcargo.authservice", "com.mcargo.common"})
public class AuthServiceApplication {

    public static void main(String[] args) {
        System.out.println("main method in");
        SpringApplication.run(AuthServiceApplication.class, args);
    }

}
