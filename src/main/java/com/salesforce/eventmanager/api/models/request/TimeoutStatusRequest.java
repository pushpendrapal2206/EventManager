package com.salesforce.eventmanager.api.models.request;

import com.salesforce.eventmanager.api.common.EventType;
import com.salesforce.eventmanager.api.common.Status;
import com.salesforce.eventmanager.api.models.request.ClientStatusRequest;
import com.salesforce.eventmanager.api.service.ClientStatusUpdateVisitor;

/**
 * @author Pushpendra Pal
 */
public class TimeoutStatusRequest extends ClientStatusRequest {
    private final Status status = Status.TIMEOUT;

    public TimeoutStatusRequest() {
        super(EventType.TIMEOUT);
    }

    @Override
    public void visit(ClientStatusUpdateVisitor handler) {
        handler.handle(this);
    }

    public Status getStatus() {
        return status;
    }
}
