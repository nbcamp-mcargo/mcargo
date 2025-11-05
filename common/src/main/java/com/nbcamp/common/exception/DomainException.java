package com.nbcamp.common.exception;

import com.nbcamp.common.response.ResponseCode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    private final ResponseCode responseCode;

    public DomainException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }
}
