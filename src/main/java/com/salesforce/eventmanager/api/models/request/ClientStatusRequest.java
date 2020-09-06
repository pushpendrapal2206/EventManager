package com.salesforce.eventmanager.api.models.request;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.salesforce.eventmanager.api.common.EventType;
import com.salesforce.eventmanager.api.service.ClientStatusUpdateVisitor;
import lombok.Data;

/**
 * @author Pushpendra Pal
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXISTING_PROPERTY, property = "type")
@JsonSubTypes(
        {
                @JsonSubTypes.Type(name = "PING", value = PingStatusRequest.class),
                @JsonSubTypes.Type(name = "TIMEOUT", value = TimeoutStatusRequest.class),
        })
public abstract class ClientStatusRequest {
    private final EventType type;
    protected String clientIp;

    protected ClientStatusRequest(EventType type) {
        this.type = type;
    }

    public abstract void visit(ClientStatusUpdateVisitor handler);

    public String getClientIp() {
        return clientIp;
    }

    public void setClientIp(String clientIp) {
        this.clientIp = clientIp;
    }
}
