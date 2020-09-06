package com.salesforce.eventmanager.api.listeners;

import com.salesforce.eventmanager.api.models.request.PingStatusRequest;
import com.salesforce.eventmanager.api.models.request.TimeoutStatusRequest;
import com.salesforce.eventmanager.api.repository.ClientStatusRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Date;

import static com.salesforce.eventmanager.api.constants.EventManagerTestConstant.DUMMY_IP_ADDRESS;

public class ClientStatusListenerTest {
    @InjectMocks
    private ClientStatusListener listener;
    @Mock
    private ClientStatusRepository clientStatusRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testUpdateWithPingStatusRequest() {
        PingStatusRequest pingStatusRequest = new PingStatusRequest();
        pingStatusRequest.setClientIp(DUMMY_IP_ADDRESS);
        Mockito.doNothing().when(clientStatusRepository)
                .createOrUpdate(Mockito.anyString(), Mockito.anyString(), Mockito.any(Date.class));

        listener.update(pingStatusRequest);

        Mockito.verify(clientStatusRepository, Mockito.times(1))
                .createOrUpdate(Mockito.anyString(), Mockito.anyString(), Mockito.any(Date.class));
    }

    @Test
    public void testUpdateWithTimeoutStatusRequest() {
        TimeoutStatusRequest timeoutStatusRequest = new TimeoutStatusRequest();
        timeoutStatusRequest.setClientIp(DUMMY_IP_ADDRESS);
        Mockito.doNothing().when(clientStatusRepository)
                .update(Mockito.anyString(), Mockito.anyString(), Mockito.any(Date.class));

        listener.update(timeoutStatusRequest);

        Mockito.verify(clientStatusRepository, Mockito.times(1))
                .update(Mockito.anyString(), Mockito.anyString(), Mockito.any(Date.class));
    }
}
