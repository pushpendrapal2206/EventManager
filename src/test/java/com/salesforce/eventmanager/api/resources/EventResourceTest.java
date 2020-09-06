package com.salesforce.eventmanager.api.resources;

import com.salesforce.eventmanager.api.common.EventType;
import com.salesforce.eventmanager.api.common.Status;
import com.salesforce.eventmanager.api.models.request.ClientStatusRequest;
import com.salesforce.eventmanager.api.models.request.ClientRequest;
import com.salesforce.eventmanager.api.models.response.ClientStatusResponse;
import com.salesforce.eventmanager.api.publisher.EventManager;
import com.salesforce.eventmanager.api.service.EventService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import javax.ws.rs.core.Response;

import static com.salesforce.eventmanager.api.constants.EventManagerTestConstant.DUMMY_IP_ADDRESS;

public class EventResourceTest {
    @InjectMocks
    private EventResource eventResource;
    @Mock
    private EventService eventService;
    @Mock
    private EventManager eventManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testEvents() {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setClientIpAddress(DUMMY_IP_ADDRESS);
        Mockito.doNothing().when(eventManager)
                .notify(Mockito.any(EventType.class), Mockito.any(ClientStatusRequest.class));

        Response response = eventResource.events(clientRequest);

        Mockito.verify(eventManager, Mockito.times(1))
                .notify(Mockito.any(EventType.class), Mockito.any(ClientStatusRequest.class));
        Assertions.assertEquals(202, response.getStatus());
    }

    @Test
    public void testClientStatus() {
        ClientRequest clientRequest = new ClientRequest();
        clientRequest.setClientIpAddress(DUMMY_IP_ADDRESS);
        Mockito.when(eventService.clientStatus(DUMMY_IP_ADDRESS))
                .thenReturn(clientStatusResponse());

        Response response = eventResource.clientStatus(DUMMY_IP_ADDRESS);

        ClientStatusResponse actualResponse = (ClientStatusResponse) response.getEntity();
        Mockito.verify(eventService, Mockito.times(1))
                .clientStatus(DUMMY_IP_ADDRESS);
        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(DUMMY_IP_ADDRESS, actualResponse.getClientIp());
        Assertions.assertEquals(Status.AVAILABLE, actualResponse.getStatus());
    }

    private ClientStatusResponse clientStatusResponse() {
        return new ClientStatusResponse(DUMMY_IP_ADDRESS, Status.AVAILABLE);
    }
}
