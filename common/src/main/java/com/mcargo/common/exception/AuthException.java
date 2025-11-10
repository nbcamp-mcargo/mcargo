package com.mcargo.common.exception;

import com.mcargo.common.response.ResponseCode;

public class AuthException extends DomainException {

    public AuthException(ResponseCode responseCode) {
        super(responseCode);
    }
}
