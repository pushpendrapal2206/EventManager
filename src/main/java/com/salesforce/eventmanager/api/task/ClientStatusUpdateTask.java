package com.salesforce.eventmanager.api.task;

import com.salesforce.eventmanager.api.common.EventType;
import com.salesforce.eventmanager.api.models.request.TimeoutStatusRequest;
import com.salesforce.eventmanager.api.publisher.EventManager;
import com.salesforce.eventmanager.api.repository.dao.ClientStatus;
import com.salesforce.eventmanager.api.service.EventService;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.TimerTask;

/**
 * @author Pushpendra Pal
 */
@Slf4j
public class ClientStatusUpdateTask extends TimerTask {
    private final EventService eventService;
    private final EventManager eventManager;

    public ClientStatusUpdateTask(final EventService eventService, final EventManager eventManager) {
        this.eventService = eventService;
        this.eventManager = eventManager;
    }

    @Override
    public void run() {
        List<ClientStatus> expiredClients = eventService.findExpiredClients();
        for (ClientStatus clientStatus : expiredClients) {
            TimeoutStatusRequest request = new TimeoutStatusRequest();
            request.setClientIp(clientStatus.getClientIp());
            eventManager.notify(EventType.TIMEOUT, request);
        }
        log.info("Updated status for {} {}", expiredClients.size(), "clients");
    }
}
