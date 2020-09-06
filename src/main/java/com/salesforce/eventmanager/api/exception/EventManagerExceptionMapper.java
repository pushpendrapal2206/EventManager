package com.salesforce.eventmanager.api.exception;

import lombok.extern.slf4j.Slf4j;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;

/**
 * @author Pushpendra Pal
 */
public class EventManagerExceptionMapper implements ExceptionMapper<EventManagerException> {
    @Override
    public Response toResponse(EventManagerException e) {
        Response.StatusType status;
        switch (e.getResponseCode()) {
            case NOT_FOUND:
                status = Response.Status.OK;
                break;
            case INVALID_IP_ADDRESS:
                status = Response.Status.BAD_REQUEST;
                break;
            default:
                status = Response.Status.INTERNAL_SERVER_ERROR;
                break;

        }
        return Response.status(status)
                .entity(GenericError.builder()
                        .code(e.getResponseCode().name())
                        .message(e.getMessage())
                        .build())
                .build();
    }
}
