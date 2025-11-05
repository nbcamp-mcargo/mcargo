package com.nbcamp.common.exception;

import com.nbcamp.common.response.ResponseCode;

public class UserException extends DomainException {

    public UserException(ResponseCode responseCode) {
        super(responseCode);
    }
}
