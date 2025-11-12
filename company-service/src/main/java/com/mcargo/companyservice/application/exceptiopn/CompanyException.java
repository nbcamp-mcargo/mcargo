package com.mcargo.companyservice.application.exceptiopn;

import com.mcargo.common.exception.DomainException;
import com.mcargo.common.response.ResponseCode;

public class CompanyException extends DomainException {

    public CompanyException(ResponseCode responseCode) {
        super(responseCode);
    }
}
