package com.mcargo.common.auth.resolver;

import com.mcargo.common.auth.context.UserContext;
import com.mcargo.common.auth.context.UserContextHolder;
import com.mcargo.common.auth.context.annotation.CurrentUser;
import com.mcargo.common.exception.AuthException;
import com.mcargo.common.response.AuthResponseCode;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class CurrentUserArgumentResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(CurrentUser.class)
            && Long.class.isAssignableFrom(parameter.getParameterType());
    }

    @Override
    public Object resolveArgument(
        MethodParameter parameter,
        ModelAndViewContainer mavContainer,
        NativeWebRequest webRequest,
        WebDataBinderFactory binderFactory) throws Exception {
        UserContext userContext = UserContextHolder.get();
        if (userContext == null) {
            throw new AuthException(AuthResponseCode.LOGIN_NEEDED);
        }
        return userContext.getUserId();
    }
}
