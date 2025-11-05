package com.nbcamp.common.exception;

import com.nbcamp.common.response.ResponseCode;

public class AuthException extends DomainException {

    public AuthException(ResponseCode responseCode) {
        super(responseCode);
    }
}
