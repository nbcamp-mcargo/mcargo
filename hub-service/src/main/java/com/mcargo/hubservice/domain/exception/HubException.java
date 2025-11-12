package com.mcargo.hubservice.domain.exception;

import com.mcargo.common.exception.DomainException;
import com.mcargo.common.response.ResponseCode;

public class HubException extends DomainException {

    public HubException(ResponseCode responseCode) {
        super(responseCode);
    }
}
