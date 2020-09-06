package com.salesforce.eventmanager.api.common;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.salesforce.eventmanager.api.common.EventManagerConstant.CREATE_CLIENT_TABLE;

public class EventManagerConstantTest {

    @Test
    public void constructorShouldBePrivate() throws Exception {
        Assertions.assertTrue(ReflectionUtils.isConstructorPrivateAndInstantiationThrowUnsupportedException(EventManagerConstant.class));
    }

    @Test
    public void createClientTableShouldBeCorrect() {
        Assertions.assertEquals("create table client_status (id varchar(20) primary key, status varchar(10), status_date datetime, index (status, status_date))",
                CREATE_CLIENT_TABLE);
    }
}
