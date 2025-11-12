package com.mcargo.gateway;

import com.mcargo.common.auth.UserRole;
import com.mcargo.common.auth.context.UserContext;
import com.mcargo.common.auth.context.UserContextHolder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserContextWebFilter implements GlobalFilter {
    // WebFlux용 UserContextFilter로 변환
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String userIdHeader = exchange.getRequest().getHeaders().getFirst("X-USER-ID");
        String rolesHeader = exchange.getRequest().getHeaders().getFirst("X-USER-ROLE");
        String authHeader = exchange.getRequest().getHeaders().getFirst("Authorization");
        log.info("userIdHeader = {}, rolesHeader = {}", userIdHeader, rolesHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return chain.filter(exchange);
        }
        if (userIdHeader != null && rolesHeader != null) {
            try {
                Long userId = Long.parseLong(userIdHeader);
                List<UserRole> roles = Arrays
                    .stream(rolesHeader.split(","))
                    .map(String::trim)
                    .map(UserRole::valueOf)
                    .toList();
                UserContextHolder.set(new UserContext(userId, roles));
                UserContext userContext = UserContextHolder.get();
                log.info("userContext.getUserId() = {}", userContext.getUserId());
                log.info("userContext.getRoles() = {}", userContext.getRoles());
            } catch (Exception e) {
                log.warn("[UserContext] Header parsing error: {}", e.getMessage());
            }
        }
        // reactive 환경에서는 doFinally로 Context 클리어
        return chain.filter(exchange)
            .doFinally(signalType -> UserContextHolder.clear());
    }
}
