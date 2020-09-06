package com.salesforce.eventmanager.api.task;

import com.salesforce.eventmanager.api.common.EventType;
import com.salesforce.eventmanager.api.common.Status;
import com.salesforce.eventmanager.api.models.request.TimeoutStatusRequest;
import com.salesforce.eventmanager.api.publisher.EventManager;
import com.salesforce.eventmanager.api.repository.dao.ClientStatus;
import com.salesforce.eventmanager.api.service.EventService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Date;
import java.util.Timer;
import java.util.concurrent.TimeUnit;

import static com.salesforce.eventmanager.api.constants.EventManagerTestConstant.DUMMY_IP_ADDRESS;

public class ClientStatusUpdateTaskTest {
    @InjectMocks
    private ClientStatusUpdateTask clientStatusUpdateTask;
    @Mock
    private EventService eventService;
    @Mock
    private EventManager eventManager;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRun() {
        Mockito.when(eventService.findExpiredClients())
                .thenReturn(Collections.singletonList(clientStatus(50)));
        Mockito.doNothing().when(eventManager)
                .notify(Mockito.any(EventType.class), Mockito.any(TimeoutStatusRequest.class));
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(clientStatusUpdateTask, new Date(), 1000);
        Mockito.verify(eventService, Mockito.times(1))
                .findExpiredClients();
        Mockito.verify(eventManager, Mockito.times(1))
                .notify(Mockito.any(EventType.class), Mockito.any(TimeoutStatusRequest.class));
        timer.cancel();
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
