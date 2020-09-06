package com.salesforce.eventmanager.api.listeners;

import com.salesforce.eventmanager.api.models.request.ClientStatusRequest;
import com.salesforce.eventmanager.api.models.request.PingStatusRequest;
import com.salesforce.eventmanager.api.models.request.TimeoutStatusRequest;
import com.salesforce.eventmanager.api.repository.ClientStatusRepository;
import com.salesforce.eventmanager.api.service.ClientStatusUpdateVisitor;

import java.util.Date;

/**
 * @author Pushpendra Pal
 */
public class ClientStatusListener implements EventListener {
    private final ClientStatusRepository clientStatusRepository;

    public ClientStatusListener(final ClientStatusRepository clientStatusRepository) {
        this.clientStatusRepository = clientStatusRepository;
    }

    @Override
    public void update(ClientStatusRequest clientStatusRequest) {
        clientStatusRequest.visit(new ClientStatusUpdateVisitor() {
            @Override
            public void handle(PingStatusRequest statusRequest) {
                clientStatusRepository.createOrUpdate(statusRequest.getClientIp(), statusRequest.getStatus().name(), new Date());
            }

            @Override
            public void handle(TimeoutStatusRequest statusRequest) {
                clientStatusRepository.update(statusRequest.getClientIp(), statusRequest.getStatus().name(), new Date());
            }
        });
    }
}
