package com.salesforce.eventmanager.api.models.request;

/**
 * @author Pushpendra Pal
 */
public class ClientRequest {
    private String clientIpAddress;

    public String getClientIpAddress() {
        return clientIpAddress;
    }

    public void setClientIpAddress(String clientIpAddress) {
        this.clientIpAddress = clientIpAddress;
    }
}
