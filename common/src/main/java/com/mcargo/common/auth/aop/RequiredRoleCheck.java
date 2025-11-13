package com.mcargo.common.auth.aop;

import com.mcargo.common.auth.UserRole;
import com.mcargo.common.auth.context.UserContext;
import com.mcargo.common.auth.context.UserContextHolder;
import com.mcargo.common.auth.context.annotation.RequiredRoles;
import com.mcargo.common.exception.AuthException;
import com.mcargo.common.response.AuthResponseCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RequiredRoleCheck {

    @Around("@annotation(requiredRoles)")
    public Object checkRequiredRoles(
        ProceedingJoinPoint joinPoint,
        RequiredRoles requiredRoles) throws Throwable {
        UserContext userContext = UserContextHolder.get();
        System.out.println("userContext.getUserId() in RoleCheck = " + userContext.getUserId());
        userContext.getRoles().forEach(role -> {
            System.out.println("role = " + role);
        });
        if (userContext.getRoles() == null) {
            throw new AuthException(AuthResponseCode.LOGIN_NEEDED);
        }
        List<UserRole> userRoles = userContext.getRoles();
        UserRole[] requiredRolesArr = requiredRoles.value();
        boolean allowed = Arrays
            .stream(requiredRolesArr)
            .anyMatch(userRoles::contains);
        if (!allowed) {
            throw new AuthException(AuthResponseCode.NO_ACCESS_RESOURCES);
        }
        return joinPoint.proceed();
    }

//    @Around("@target(com.mcargo.common.auth.annotation.RequiredRoles)")
//    public Object checkRole(ProceedingJoinPoint pjp) throws Throwable {
//        // 1 요청 객체에서 역할 헤더를 읽어줍니다.
//        HttpServletRequest request = getCurrentHttpRequest();
//        if (request == null) {
//            throw new AuthException(AuthResponseCode.NO_HTTP_REQUEST);
//        }
//        String roleHeader = request.getHeader(HEADER_ROLE);
//        if (roleHeader == null || roleHeader.isBlank()) {
//            throw new AuthException(AuthResponseCode.MISSING_ROLE);
//        }
//        // 2 읽어야 할 허용 role 목록을 메서드 또는 클래스에서 찾아줍니다.
//        MethodSignature signature = (MethodSignature) pjp.getSignature();
//        Method method = signature.getMethod();
//        RequiredRoles reqRoles = method.getAnnotation(RequiredRoles.class);
//        if (reqRoles == null) {
//            // 메서드에 없으면 클래스 레벨 확인해줍니다.
//            reqRoles = pjp.getTarget().getClass().getAnnotation(RequiredRoles.class);
//            // 어노테이션이 없다면 허용해줍니다(하지만 pointcut으로 진입한 경우엔 어노테이션이 있어야 합니다).
//            return pjp.proceed();
//        }
//        Set<String> allowed = Arrays.stream(reqRoles.value())
//            .map(UserRole::name)
//            .collect(Collectors.toSet());
//
//        // roleHeader는 게이트웨이에서 전달한 값(ex: "MASTER")이라고 가정
//        if (!allowed.contains(roleHeader)) {
//            throw new AuthException(AuthResponseCode.INSUFFICIENT_ROLE,
//                "required=" + allowed + " but was=" + roleHeader);
//        }
//        return pjp.proceed();
//    }
//
//    private HttpServletRequest getCurrentHttpRequest() {
//        RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
//        if (attrs instanceof ServletRequestAttributes) {
//            return ((ServletRequestAttributes) attrs).getRequest();
//        }
//        return null;
//    }
}
