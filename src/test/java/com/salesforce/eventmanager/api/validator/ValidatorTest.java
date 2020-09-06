package com.salesforce.eventmanager.api.validator;

import com.salesforce.eventmanager.api.common.ResponseCode;
import com.salesforce.eventmanager.api.constants.EventManagerTestConstant;
import com.salesforce.eventmanager.api.exception.EventManagerException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.salesforce.eventmanager.api.constants.EventManagerTestConstant.DUMMY_INVALID_IP_ADDRESS;
import static com.salesforce.eventmanager.api.constants.EventManagerTestConstant.DUMMY_IP_ADDRESS;

public class ValidatorTest {

    @Test
    public void shouldReturnTrueForValidIpAddress() {
        boolean isValid = Validator.validateIpAddress(DUMMY_IP_ADDRESS);
        Assertions.assertTrue(isValid);
    }

    @Test
    public void shouldThrowExceptionForInValidIpAddress() {
        EventManagerException exception = Assertions.assertThrows(EventManagerException.class,
                () -> Validator.validateIpAddress(DUMMY_INVALID_IP_ADDRESS));
        Assertions.assertEquals(ResponseCode.INVALID_IP_ADDRESS, exception.getResponseCode());
    }
}
