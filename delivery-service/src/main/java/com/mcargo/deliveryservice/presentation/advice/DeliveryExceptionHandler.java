package com.mcargo.deliveryservice.presentation.advice;

import com.mcargo.deliveryservice.domain.exception.DeliveryException;
import com.mcargo.common.exception.GlobalExceptionHandler;
import com.mcargo.common.response.ApiResponse;
import com.mcargo.common.response.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class DeliveryExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(DeliveryException.class)
    public ResponseEntity<ApiResponse<?>> handleDeliveryException(DeliveryException e) {
        ResponseCode code = e.getResponseCode();

        log.error("[deliveryException] {} ({})",
                code.getHttpStatus(),
                e.getMessage()
        );

        return ResponseEntity.status(
                e.getResponseCode().getHttpStatus()).body(
                ApiResponse.of(e.getResponseCode())
        );
    }
}
