package com.mcargo.common.exception;

import com.mcargo.common.response.ResponseCode;

public class OrderException extends DomainException {

    public OrderException(ResponseCode responseCode) {
        super(responseCode);
    }
}
