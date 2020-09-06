package com.salesforce.eventmanager.api.exception;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;

import static com.salesforce.eventmanager.api.common.ResponseCode.INVALID_IP_ADDRESS;
import static com.salesforce.eventmanager.api.common.ResponseCode.NOT_FOUND;
import static com.salesforce.eventmanager.api.common.ResponseCode.UNDEFINED;

public class EventManagerExceptionMapperTest {

    @Test
    public void testToResponseForNotFoundResponseCode() {
        EventManagerExceptionMapper mapper = new EventManagerExceptionMapper();

        Response response = mapper.toResponse(EventManagerException.create(NOT_FOUND));
        GenericError genericError = (GenericError) response.getEntity();

        Assertions.assertEquals(200, response.getStatus());
        Assertions.assertEquals(NOT_FOUND.name(), genericError.getCode());
        Assertions.assertEquals(NOT_FOUND.name(), genericError.getMessage());
    }

    @Test
    public void testToResponseForInvalidIpAddressResponseCode() {
        EventManagerExceptionMapper mapper = new EventManagerExceptionMapper();

        Response response = mapper.toResponse(EventManagerException.create(INVALID_IP_ADDRESS));
        GenericError genericError = (GenericError) response.getEntity();

        Assertions.assertEquals(400, response.getStatus());
        Assertions.assertEquals(INVALID_IP_ADDRESS.name(), genericError.getCode());
        Assertions.assertEquals(INVALID_IP_ADDRESS.name(), genericError.getMessage());
    }

    @Test
    public void testToResponseForUndefineErrorCode() {
        EventManagerExceptionMapper mapper = new EventManagerExceptionMapper();

        Response response = mapper.toResponse(EventManagerException.create(UNDEFINED));
        GenericError genericError = (GenericError) response.getEntity();

        Assertions.assertEquals(500, response.getStatus());
        Assertions.assertEquals(UNDEFINED.name(), genericError.getCode());
        Assertions.assertEquals(UNDEFINED.name(), genericError.getMessage());
    }
}
