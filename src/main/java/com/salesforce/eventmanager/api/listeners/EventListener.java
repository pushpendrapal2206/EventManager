package com.salesforce.eventmanager.api.listeners;

import com.salesforce.eventmanager.api.models.request.ClientStatusRequest;

/**
 * @author Pushpendra Pal
 */
public interface EventListener {
    void update(ClientStatusRequest clientStatusRequest);
}
