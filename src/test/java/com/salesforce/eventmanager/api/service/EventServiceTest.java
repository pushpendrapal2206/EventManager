package com.salesforce.eventmanager.api.service;

import com.salesforce.eventmanager.api.common.EventType;
import com.salesforce.eventmanager.api.common.ResponseCode;
import com.salesforce.eventmanager.api.common.Status;
import com.salesforce.eventmanager.api.configuration.AppConfig;
import com.salesforce.eventmanager.api.exception.EventManagerException;
import com.salesforce.eventmanager.api.models.request.ClientStatusRequest;
import com.salesforce.eventmanager.api.models.response.ClientStatusResponse;
import com.salesforce.eventmanager.api.publisher.EventManager;
import com.salesforce.eventmanager.api.repository.ClientStatusRepository;
import com.salesforce.eventmanager.api.repository.dao.ClientStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static com.salesforce.eventmanager.api.constants.EventManagerTestConstant.DUMMY_IP_ADDRESS;

public class EventServiceTest {

    @InjectMocks
    private EventService eventService;
    @Mock
    private ClientStatusRepository statusRepository;
    @Mock
    private EventManager eventManager;
    @Mock
    private AppConfig appConfig;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void shouldReturnClientStatus() {
        Mockito.when(statusRepository.findClientStatusById(DUMMY_IP_ADDRESS))
                .thenReturn(clientStatus(50));

        ClientStatusResponse clientStatusResponse = eventService.clientStatus(DUMMY_IP_ADDRESS);

        Mockito.verify(statusRepository, Mockito.times(1))
                .findClientStatusById(DUMMY_IP_ADDRESS);
        Assertions.assertEquals(Status.AVAILABLE, clientStatusResponse.getStatus());
        Assertions.assertEquals(DUMMY_IP_ADDRESS, clientStatusResponse.getClientIp());
    }

    @Test
    public void shouldThrowExceptionWhenIpAddressIsNotFound() {
        Mockito.when(statusRepository.findClientStatusById(DUMMY_IP_ADDRESS))
                .thenReturn(null);

        EventManagerException exception = Assertions.assertThrows(EventManagerException.class,
                () -> eventService.clientStatus(DUMMY_IP_ADDRESS));

        Mockito.verify(statusRepository, Mockito.times(1))
                .findClientStatusById(DUMMY_IP_ADDRESS);
        Assertions.assertEquals(ResponseCode.NOT_FOUND, exception.getResponseCode());
    }

    @Test
    public void shouldRetunClientStatusWithTimeoutForExpiredStatus() {
        Mockito.when(statusRepository.findClientStatusById(DUMMY_IP_ADDRESS))
                .thenReturn(clientStatus(-50));
        Mockito.doNothing().when(eventManager)
                .notify(Mockito.any(EventType.class), Mockito.any(ClientStatusRequest.class));

        ClientStatusResponse clientStatusResponse = eventService.clientStatus(DUMMY_IP_ADDRESS);

        Mockito.verify(statusRepository, Mockito.times(1))
                .findClientStatusById(DUMMY_IP_ADDRESS);
        Mockito.verify(eventManager, Mockito.times(1))
                .notify(Mockito.any(EventType.class), Mockito.any(ClientStatusRequest.class));
        Assertions.assertEquals(Status.TIMEOUT, clientStatusResponse.getStatus());
        Assertions.assertEquals(DUMMY_IP_ADDRESS, clientStatusResponse.getClientIp());
    }

    @Test
    public void shouldReturnExpiredClients() {
        Mockito.when(statusRepository.findExpiredByStatus(Mockito.anyString(), Mockito.any(Date.class)))
                .thenReturn(Collections.singletonList(clientStatus(-50)));
        List<ClientStatus> clientStatusList = eventService.findExpiredClients();

        Mockito.verify(statusRepository, Mockito.timeout(1))
                .findExpiredByStatus(Mockito.anyString(), Mockito.any(Date.class));
        Assertions.assertEquals(1, clientStatusList.size());
    }

    private ClientStatus clientStatus(long threshold) {
        ClientStatus clientStatus = new ClientStatus(DUMMY_IP_ADDRESS);
        clientStatus.setStatus(Status.AVAILABLE);
        if (threshold > 0) {
            clientStatus.setDate(new Date(System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(Math.abs(threshold))));
        } else {

            clientStatus.setDate(new Date(System.currentTimeMillis() - TimeUnit.SECONDS.toMillis(Math.abs(threshold))));
        }
        return clientStatus;
    }

}
