package com.mcargo.common.auth.interceptor;

import com.mcargo.common.auth.UserRole;
import com.mcargo.common.auth.context.UserContext;
import com.mcargo.common.auth.context.UserContextHolder;
import com.mcargo.common.exception.AuthException;
import com.mcargo.common.response.AuthResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

@Slf4j
public class UserContextHolderInterceptor implements HandlerInterceptor {
    private static final List<String> WHITE_LIST = List.of(
        "/auths/signUp",
        "/auths/login",
        "/auths/refresh"
    );

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String path = request.getRequestURI();
        if (WHITE_LIST.contains(path)) {
            return true;
        }

        String userIdHeader = request.getHeader("X-USER-ID");
        String currentUserRole = request.getHeader("X-USER-ROLE");
        // 1 헤더 존재 여부 확인
//        if (userIdHeader == null || userIdHeader.isBlank()) {
//            throw new AuthException(AuthResponseCode.MISSING_USER_ID);
//        }
//        if (currentUserRole == null || currentUserRole.isBlank()) {
//            throw new AuthException(AuthResponseCode.MISSING_USER_ROLE);
//        }
        // 2 Long parsing 예외처리
        Long currentUserId;
        try {
            currentUserId = Long.parseLong(userIdHeader);
        } catch (AuthException e) {
            throw new AuthException(AuthResponseCode.INVALID_USER_ID);
        }
        // 3 Enum 변환 처리

        // String "MASTER" -> UserRole.MASTER
        UserRole userRole = getRoleStringToUserRoleObject(currentUserRole);
        List<UserRole> currentUserRoles = List.of(userRole);

        UserContext userContext = new UserContext(currentUserId, currentUserRoles);
        UserContextHolder.set(userContext);
        return true;
    }

    @Override
    public void afterCompletion(
        HttpServletRequest request,
        HttpServletResponse response,
        Object handler,
        @Nullable Exception ex) throws Exception {
        try {
            log.info("afterCompletion called");
        } finally {
            UserContextHolder.clear();
        }
    }

    private static UserRole getRoleStringToUserRoleObject(String currentUserRole) {
        UserRole userRole = Arrays.stream(UserRole.values())
            .filter(it -> it.name().equals(currentUserRole))
            .findFirst()
            .orElseThrow();
        return userRole;
    }
}
