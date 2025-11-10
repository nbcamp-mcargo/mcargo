package com.mcargo.common.exception;

import com.mcargo.common.response.ResponseCode;

public class HubException extends DomainException {

    public HubException(ResponseCode responseCode) {
        super(responseCode);
    }
}
