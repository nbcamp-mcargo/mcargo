package com.mcargo.authservice.domain.exception;

import com.mcargo.common.exception.DomainException;
import com.mcargo.common.response.ResponseCode;

public class UserException extends DomainException {

    public UserException(ResponseCode responseCode) {
        super(responseCode);
    }
}
