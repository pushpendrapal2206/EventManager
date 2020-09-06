package com.salesforce.eventmanager.api.common;

/**
 * @author Pushpendra Pal
 */
public final class EventManagerConstant {
    public static final String CREATE_CLIENT_TABLE = "create table client_status (id varchar(20) primary key, status varchar(10), status_date datetime, index (status, status_date))";
    public static final String OPERATION_NOT_SUPPORTED = "Operation not supported";


    private EventManagerConstant() {
        throw new UnsupportedOperationException(OPERATION_NOT_SUPPORTED);
    }
}
