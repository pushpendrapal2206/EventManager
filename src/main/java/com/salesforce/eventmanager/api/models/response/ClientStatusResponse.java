package com.salesforce.eventmanager.api.models.response;

import com.salesforce.eventmanager.api.common.Status;

public class ClientStatusResponse {
    private final String clientIp;
    private final Status status;

    public ClientStatusResponse(final String clientIp, final Status status) {
        this.clientIp = clientIp;
        this.status = status;
    }

    public String getClientIp() {
        return clientIp;
    }

    public Status getStatus() {
        return status;
    }
}
