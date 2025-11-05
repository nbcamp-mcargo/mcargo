package com.nbcamp.common.exception;

import com.nbcamp.common.response.ResponseCode;

public class OrderException extends DomainException {

    public OrderException(ResponseCode responseCode) {
        super(responseCode);
    }
}
