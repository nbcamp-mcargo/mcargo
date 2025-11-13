package com.mcargo.common.auth.interceptor;

import com.mcargo.common.auth.UserRole;
import com.mcargo.common.auth.context.UserContext;
import com.mcargo.common.auth.context.UserContextHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;
import java.util.List;

public class UserContextHolderInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Long currentUserId = Long.parseLong(request.getHeader("X-USER-ID"));
        String currentUserRole = request.getHeader("X-USER-ROLE");

        // String "MASTER" -> UserRole.MASTER
        UserRole userRole = getRoleStringToUserRoleObject(currentUserRole);
        List<UserRole> currentUserRoles = List.of(userRole);

        UserContext userContext = new UserContext(currentUserId, currentUserRoles);
        UserContextHolder.set(userContext);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        UserContextHolder.clear();
    }

    private static UserRole getRoleStringToUserRoleObject(String currentUserRole) {
        UserRole userRole = Arrays.stream(UserRole.values())
            .filter(it -> it.name().equals(currentUserRole))
            .findFirst()
            .orElseThrow();
        return userRole;
    }
}
