package com.mcargo.common.exception;

import com.mcargo.common.response.ResponseCode;
import lombok.Getter;

@Getter
public class DomainException extends RuntimeException {

    private final ResponseCode responseCode;

    public DomainException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }
}
