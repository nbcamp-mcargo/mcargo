package com.mcargo.common.exception;

import com.mcargo.common.response.ResponseCode;

public class AuthException extends DomainException {

    private final String detailMessage;

    public AuthException(ResponseCode responseCode) {
        super(responseCode);
        this.detailMessage = null;
    }

    public AuthException(ResponseCode responseCode, String detailMessage) {
        super(responseCode);
        this.detailMessage = detailMessage;
    }
}
