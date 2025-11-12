package com.mcargo.orderservice.domain.exception;

import com.mcargo.common.exception.DomainException;
import com.mcargo.common.response.ResponseCode;

public class OrderException extends DomainException {

    public OrderException(ResponseCode responseCode) {
        super(responseCode);
    }
}
