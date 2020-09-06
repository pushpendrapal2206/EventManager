package com.salesforce.eventmanager.api.exception;

import com.salesforce.eventmanager.api.common.ResponseCode;

/**
 * @author Pushpendra Pal
 */
public class EventManagerException extends RuntimeException {

    private final ResponseCode responseCode;

    protected EventManagerException(ResponseCode responseCode, String message, Throwable t) {
        super(message, t);
        this.responseCode = responseCode;
    }

    public static EventManagerException create(ResponseCode responseCode) {
        return new EventManagerException(responseCode, responseCode.name(), (Throwable) null);
    }

    public ResponseCode getResponseCode() {
        return this.responseCode;
    }
}
