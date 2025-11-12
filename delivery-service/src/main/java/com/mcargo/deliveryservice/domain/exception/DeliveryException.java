package com.mcargo.deliveryservice.domain.exception;

import com.mcargo.common.exception.DomainException;
import com.mcargo.common.response.ResponseCode;

public class DeliveryException extends DomainException {

    public DeliveryException(ResponseCode responseCode) {
        super(responseCode);
    }
}
