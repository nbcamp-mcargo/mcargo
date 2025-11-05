package com.nbcamp.common.exception;

import com.nbcamp.common.response.ResponseCode;

public class HubException extends DomainException {

    public HubException(ResponseCode responseCode) {
        super(responseCode);
    }
}
