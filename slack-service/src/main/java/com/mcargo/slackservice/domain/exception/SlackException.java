package com.mcargo.slackservice.domain.exception;

import com.mcargo.common.exception.DomainException;
import com.mcargo.common.response.ResponseCode;

public class SlackException extends DomainException {
    public SlackException(ResponseCode responseCode) {
        super(responseCode);
    }
}
