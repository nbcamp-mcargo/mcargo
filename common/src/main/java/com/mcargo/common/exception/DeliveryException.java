package com.mcargo.common.exception;

import com.mcargo.common.response.ResponseCode;

public class DeliveryException extends DomainException {

    public DeliveryException(ResponseCode responseCode) {
        super(responseCode);
    }
}
