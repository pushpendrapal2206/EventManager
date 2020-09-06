package com.salesforce.eventmanager.api.validator;

import com.salesforce.eventmanager.api.common.ResponseCode;
import com.salesforce.eventmanager.api.exception.EventManagerException;

import java.util.regex.Pattern;

/**
 * @author Pushpendra Pal
 */
public class Validator {
    private static final String zeroTo255
            = "(\\d{1,2}|(0|1)\\"
            + "d{2}|2[0-4]\\d|25[0-5])";

    private static final String regex
            = zeroTo255 + "\\."
            + zeroTo255 + "\\."
            + zeroTo255 + "\\."
            + zeroTo255;

    private static final Pattern p = Pattern.compile(regex);

    public static boolean validateIpAddress(String ipAddress) {
        if (ipAddress == null || !p.matcher(ipAddress).matches()) {
            throw EventManagerException.create(ResponseCode.INVALID_IP_ADDRESS);
        }
        return true;
    }
}
