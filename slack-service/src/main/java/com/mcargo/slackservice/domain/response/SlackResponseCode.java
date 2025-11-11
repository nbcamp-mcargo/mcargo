package com.mcargo.slackservice.domain.response;

import com.mcargo.common.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum SlackResponseCode implements ResponseCode {

    SLACK_OK(HttpStatus.OK, "성공적으로 메시지를 송신했습니다."),

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    SLACK_FAIL(HttpStatus.INTERNAL_SERVER_ERROR, "메시지 송신에 실패했습니다.");

    private final HttpStatus httpStatus;

    private final String message;


}
