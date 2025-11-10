package com.nbcamp.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableFeignClients
public class DeliveryApplication {

    @Configuration
    @Profile("!test")
    @EnableJpaAuditing
    static class JpaAuditingConfig {
    }

    public static void main(String[] args) {
        SpringApplication.run(DeliveryApplication.class, args);
    }
}