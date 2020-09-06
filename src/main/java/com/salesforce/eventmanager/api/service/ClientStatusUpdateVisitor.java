package com.salesforce.eventmanager.api.service;

import com.salesforce.eventmanager.api.models.request.PingStatusRequest;
import com.salesforce.eventmanager.api.models.request.TimeoutStatusRequest;

/**
 * @author Pushpendra Pal
 */
public interface ClientStatusUpdateVisitor {
    void handle(PingStatusRequest statusRequest);

    void handle(TimeoutStatusRequest statusRequest);
}
