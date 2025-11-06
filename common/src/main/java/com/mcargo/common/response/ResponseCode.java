package com.mcargo.common.response;

import org.springframework.http.HttpStatus;

public interface ResponseCode {
    HttpStatus getHttpStatus();
    String getMessage();
}
