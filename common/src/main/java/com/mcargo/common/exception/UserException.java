package com.mcargo.common.exception;

import com.mcargo.common.response.ResponseCode;

public class UserException extends DomainException {

    public UserException(ResponseCode responseCode) {
        super(responseCode);
    }
}
