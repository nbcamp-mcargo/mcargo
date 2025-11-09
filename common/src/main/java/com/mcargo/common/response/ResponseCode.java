package com.mcargo.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.http.HttpStatus;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public interface ResponseCode {
    HttpStatus getHttpStatus();
    String getMessage();
}
