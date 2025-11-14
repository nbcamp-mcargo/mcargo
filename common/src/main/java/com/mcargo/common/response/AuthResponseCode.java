package com.mcargo.common.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthResponseCode implements ResponseCode {
    // 권한 status code
    MISSING_USER_ID(HttpStatus.UNAUTHORIZED, "X-USER-ID 헤더 값이 누락되었습니다."),
    INVALID_USER_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 X-USER-ID 헤더입니다."),
    NO_HTTP_REQUEST(HttpStatus.INTERNAL_SERVER_ERROR, "현재 HTTP 요청이 없습니다."),
    NO_REQUEST_AVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, "현재는 요청 불가능합니다."),
    INSUFFICIENT_ROLE(HttpStatus.FORBIDDEN, "불충분한 역할입니다."),
    LOGIN_NEEDED(HttpStatus.UNAUTHORIZED, "로그인이 필요한 요청입니다."),
    NO_ACCESS_RESOURCES(HttpStatus.FORBIDDEN, "자원에 접근할 권한이 없습니다.");

    private final HttpStatus httpStatus;
    private final String message;
}
