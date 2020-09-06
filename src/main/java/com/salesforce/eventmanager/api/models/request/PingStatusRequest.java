package com.salesforce.eventmanager.api.models.request;

import com.salesforce.eventmanager.api.common.EventType;
import com.salesforce.eventmanager.api.common.Status;
import com.salesforce.eventmanager.api.service.ClientStatusUpdateVisitor;

/**
 * @author Pushpendra Pal
 */
public class PingStatusRequest extends ClientStatusRequest {
    private final Status status = Status.AVAILABLE;

    public PingStatusRequest() {
        super(EventType.PING);
    }

    @Override
    public void visit(ClientStatusUpdateVisitor handler) {
        handler.handle(this);
    }

    public Status getStatus() {
        return status;
    }
}
