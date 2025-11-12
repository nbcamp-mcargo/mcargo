package com.mcargo.authservice.domain.exception;

import com.mcargo.common.exception.DomainException;
import com.mcargo.common.response.ResponseCode;

public class UserException extends DomainException {

    private final String detailMessage;

    public UserException(ResponseCode responseCode) {
        super(responseCode);
        this.detailMessage = null;
    }

    public UserException(ResponseCode responseCode, String detailMessage) {
        super(responseCode);
        this.detailMessage = detailMessage;
    }

}
