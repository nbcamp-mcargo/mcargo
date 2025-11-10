package com.mcargo.common.exception;

import com.mcargo.common.response.ApiResponse;
import com.mcargo.common.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DomainException.class)
    public ResponseEntity<ApiResponse<?>> handleDomainException(DomainException e) {

        ResponseCode code = e.getResponseCode();

        log.error("[{}] {} ({})",
                e.getClass().getSimpleName(),
                code.getHttpStatus(),
                e.getMessage()
        );

        return ResponseEntity.status(
                e.getResponseCode().getHttpStatus()).body(
                        ApiResponse.of(e.getResponseCode())
                );
    }

}
