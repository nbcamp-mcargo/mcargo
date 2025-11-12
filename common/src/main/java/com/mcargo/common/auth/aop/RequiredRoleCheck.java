package com.mcargo.common.auth.aop;

import com.mcargo.common.auth.UserRole;
import com.mcargo.common.auth.annotation.RequiredRoles;
import com.mcargo.common.exception.UserException;
import com.mcargo.common.response.UserResponseCode;
import jakarta.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
public class RequiredRoleCheck {


    private static final String HEADER_ROLE = "X-Role";

    @Around("@target(com.mcargo.common.auth.annotation.RequiredRoles)")
    public Object checkRole(ProceedingJoinPoint pjp) throws Throwable {
        // 1 요청 객체에서 역할 헤더를 읽어줍니다.
        HttpServletRequest request = getCurrentHttpRequest();
        if (request == null) {
            throw new UserException(UserResponseCode.NO_HTTP_REQUEST);
        }
        String roleHeader = request.getHeader(HEADER_ROLE);
        if (roleHeader == null || roleHeader.isBlank()) {
            throw new UserException(UserResponseCode.MISSING_ROLE);
        }
        // 2 읽어야 할 허용 role 목록을 메서드 또는 클래스에서 찾아줍니다.
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        Method method = signature.getMethod();
        RequiredRoles reqRoles = method.getAnnotation(RequiredRoles.class);
        if (reqRoles == null) {
            // 메서드에 없으면 클래스 레벨 확인해줍니다.
            reqRoles = pjp.getTarget().getClass().getAnnotation(RequiredRoles.class);
            // 어노테이션이 없다면 허용해줍니다(하지만 pointcut으로 진입한 경우엔 어노테이션이 있어야 합니다).
            return pjp.proceed();
        }
        Set<String> allowed = Arrays.stream(reqRoles.value())
            .map(UserRole::name)
            .collect(Collectors.toSet());

        // roleHeader는 게이트웨이에서 전달한 값(ex: "MASTER")이라고 가정
        if (!allowed.contains(roleHeader)) {
            throw new UserException(UserResponseCode.INSUFFICIENT_ROLE,
                "required=" + allowed + " but was=" + roleHeader);
        }
        return pjp.proceed();
    }

    private HttpServletRequest getCurrentHttpRequest() {
        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
        if (attrs instanceof ServletRequestAttributes) {
            return ((ServletRequestAttributes) attrs).getRequest();
        }
        return null;
    }
}
