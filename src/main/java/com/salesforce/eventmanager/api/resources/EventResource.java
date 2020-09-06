package com.salesforce.eventmanager.api.resources;


import com.salesforce.eventmanager.api.common.EventType;
import com.salesforce.eventmanager.api.models.request.ClientRequest;
import com.salesforce.eventmanager.api.models.request.PingStatusRequest;
import com.salesforce.eventmanager.api.publisher.EventManager;
import com.salesforce.eventmanager.api.service.EventService;
import com.salesforce.eventmanager.api.validator.Validator;

import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * @author Pushpendra Pal
 */
@Path("/event")
@Produces(MediaType.APPLICATION_JSON)
public class EventResource {
    private final EventService eventService;
    private final EventManager eventManager;

    public EventResource(final EventService eventService, final EventManager eventManager) {
        this.eventService = eventService;
        this.eventManager = eventManager;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response events(ClientRequest clientRequest) {
        Validator.validateIpAddress(clientRequest.getClientIpAddress());
        PingStatusRequest request = new PingStatusRequest();
        request.setClientIp(clientRequest.getClientIpAddress());
        eventManager.notify(EventType.PING, request);
        return Response.accepted().build();
    }

    @Path("/{clientIp}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    public Response clientStatus(@NotNull @PathParam("clientIp") String clientIp) {
        Validator.validateIpAddress(clientIp);
        return Response.ok()
                .entity(eventService.clientStatus(clientIp))
                .build();
    }
}
