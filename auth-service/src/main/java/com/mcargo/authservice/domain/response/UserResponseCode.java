package com.mcargo.authservice.domain.response;

import com.mcargo.common.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum UserResponseCode implements ResponseCode {
    // http status code
    USER_CREATED(HttpStatus.CREATED, "새로운 사용자가 생성되었습니다."),
    USER_LOGIN(HttpStatus.OK, "사용자가 로그인 했습니다."),
    USER_LOGOUT(HttpStatus.OK, "사용자가 로그아웃 했습니다."),
    USER_LIST(HttpStatus.OK, "사용자 목록 조회되었습니다."),
    USER_ONE(HttpStatus.OK, "특정 사용자 조회되었습니다."),
    USER_ME(HttpStatus.OK, "내 정보 조회합니다."),
    USER_UPDATE(HttpStatus.OK, "내 정보 수정합니다."),
    USER_SEARCH(HttpStatus.OK, "조건으로 사용자 목록 조회되었습니다."),
    USER_WITHDRAW(HttpStatus.NO_CONTENT, "유저 탈퇴 요청을 성공했습니다."),
    // error status code
    INVALID_LOGIN_CREDENTIALS(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 일치하지 않습니다."),
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 리프레시 토큰입니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 리프레시 토큰입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다."),
    MISSING_USER_ID(HttpStatus.UNAUTHORIZED, "X-User-Id 헤더 값이 누락되었습니다."),
    INVALID_USER_ID(HttpStatus.BAD_REQUEST, "유효하지 않은 X-User-Id 헤더입니다."),
    MISSING_ROLE(HttpStatus.FORBIDDEN, "role 헤더 값이 누락되었습니다."),
    NO_HTTP_REQUEST(HttpStatus.INTERNAL_SERVER_ERROR, "현재 HTTP 요청이 없습니다."),
    NO_REQUEST_AVAILABLE(HttpStatus.INTERNAL_SERVER_ERROR, "현재는 요청 불가능합니다."),
    INSUFFICIENT_ROLE(HttpStatus.FORBIDDEN, "불충분한 역할입니다."),
    // duplicate data
    DUPLICATE_USERNAME(HttpStatus.CONFLICT, "이미 존재하는 사용자명입니다."),
    DUPLICATE_NICKNAME(HttpStatus.CONFLICT, "이미 존재하는 닉네임입니다."),
    DUPLICATE_EMAIL(HttpStatus.CONFLICT, "이미 존재하는 이메일입니다."),
    // password error code
    CURRENT_PASSWORD_REQUIRED(HttpStatus.BAD_REQUEST, "비밀번호 변경할 때 현재 비밀번호는 필수입니다."),
    CURRENT_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "현재 비밀번호가 일치하지 않습니다."),
    PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "비밀번호가 일치하지 않습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}
