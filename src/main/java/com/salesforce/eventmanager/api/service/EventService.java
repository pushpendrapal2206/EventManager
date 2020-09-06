package com.salesforce.eventmanager.api.service;

import com.salesforce.eventmanager.api.common.EventType;
import com.salesforce.eventmanager.api.common.ResponseCode;
import com.salesforce.eventmanager.api.common.Status;
import com.salesforce.eventmanager.api.configuration.AppConfig;
import com.salesforce.eventmanager.api.exception.EventManagerException;
import com.salesforce.eventmanager.api.models.request.TimeoutStatusRequest;
import com.salesforce.eventmanager.api.models.response.ClientStatusResponse;
import com.salesforce.eventmanager.api.publisher.EventManager;
import com.salesforce.eventmanager.api.repository.dao.ClientStatus;
import com.salesforce.eventmanager.api.repository.ClientStatusRepository;


import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Pushpendra Pal
 */
public class EventService {
    private final ClientStatusRepository clientStatusRepository;
    private final EventManager eventManager;
    private final AppConfig appConfig;

    public EventService(final ClientStatusRepository clientStatusRepository,
                        final EventManager eventManager,
                        final AppConfig appConfig) {
        this.clientStatusRepository = clientStatusRepository;
        this.eventManager = eventManager;
        this.appConfig = appConfig;
    }

    public ClientStatusResponse clientStatus(String clientIp) {
        ClientStatus clientStatus = clientStatusRepository.findClientStatusById(clientIp);
        if (isExpired(clientStatus)) {
            TimeoutStatusRequest request = new TimeoutStatusRequest();
            request.setClientIp(clientStatus.getClientIp());
            eventManager.notify(EventType.TIMEOUT, request);
            return new ClientStatusResponse(clientStatus.getClientIp(), Status.TIMEOUT);
        }
        return new ClientStatusResponse(clientStatus.getClientIp(), clientStatus.getStatus());
    }

    public List<ClientStatus> findExpiredClients() {
        Date expiryDateTime = new Date(System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(appConfig.getThresholdInSeconds()));
        return clientStatusRepository.findExpiredByStatus(Status.AVAILABLE.name(), expiryDateTime);
    }

    private boolean isExpired(ClientStatus clientStatus) {
        if (Objects.isNull(clientStatus)) {
            throw EventManagerException.create(ResponseCode.NOT_FOUND);
        }
        Date expiryDateTime = new Date(System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(appConfig.getThresholdInSeconds()));
        return clientStatus.getDate().before(expiryDateTime) && clientStatus.getStatus() != Status.TIMEOUT;
    }
}
