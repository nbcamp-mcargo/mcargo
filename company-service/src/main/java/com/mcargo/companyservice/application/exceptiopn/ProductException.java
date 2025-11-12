package com.mcargo.companyservice.application.exceptiopn;

import com.mcargo.common.exception.DomainException;
import com.mcargo.common.response.ResponseCode;

public class ProductException extends DomainException {

    public ProductException(ResponseCode responseCode) {
        super(responseCode);
    }
}
