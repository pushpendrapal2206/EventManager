package com.salesforce.eventmanager.api.publisher;

import com.salesforce.eventmanager.api.common.EventType;
import com.salesforce.eventmanager.api.listeners.ClientStatusListener;
import com.salesforce.eventmanager.api.listeners.EventListener;
import com.salesforce.eventmanager.api.models.request.ClientStatusRequest;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class EventManagerTest {
    private final EventManager eventManager = new EventManager();
    private final EventListener eventListener = Mockito.mock(ClientStatusListener.class);
    private final ClientStatusRequest clientStatusRequest = Mockito.mock(ClientStatusRequest.class);

    @Test
    public void testNotify() {
        eventManager.subscribe(EventType.PING, eventListener);
        Mockito.doNothing().when(eventListener)
                .update(clientStatusRequest);
        eventManager.notify(EventType.PING, clientStatusRequest);
        Mockito.verify(eventListener, Mockito.times(1))
                .update(clientStatusRequest);
        eventManager.unsubscribe(EventType.PING, eventListener);
    }
}
