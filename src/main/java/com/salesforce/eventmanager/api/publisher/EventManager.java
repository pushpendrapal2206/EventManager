package com.salesforce.eventmanager.api.publisher;

import com.salesforce.eventmanager.api.common.EventType;
import com.salesforce.eventmanager.api.listeners.EventListener;
import com.salesforce.eventmanager.api.models.request.ClientStatusRequest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Pushpendra Pal
 */
public class EventManager {
    private final Map<EventType, List<EventListener>> listeners = new HashMap<>();

    public void subscribe(EventType eventType, EventListener listener) {
        listeners.computeIfAbsent(eventType, z-> new ArrayList<>()).add(listener);
    }

    public void unsubscribe(EventType eventType, EventListener listener) {
        List<EventListener> users = listeners.get(eventType);
        users.remove(listener);
    }

    public void notify(EventType eventType, ClientStatusRequest clientStatus) {
        List<EventListener> users = listeners.get(eventType);
        for (EventListener listener : users) {
            listener.update(clientStatus);
        }
    }

}
