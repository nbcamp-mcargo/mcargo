package com.nbcamp.common.exception;

import com.nbcamp.common.response.ResponseCode;

public class DeliveryException extends DomainException {

    public DeliveryException(ResponseCode responseCode) {
        super(responseCode);
    }
}
