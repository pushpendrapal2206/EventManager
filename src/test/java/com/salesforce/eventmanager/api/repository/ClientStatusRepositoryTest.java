package com.salesforce.eventmanager.api.repository;

import com.codahale.metrics.MetricRegistry;
import com.salesforce.eventmanager.api.common.Status;
import com.salesforce.eventmanager.api.repository.dao.ClientStatus;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.jackson.Jackson;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skife.jdbi.v2.DBI;
import org.skife.jdbi.v2.Handle;

import java.util.Date;
import java.util.List;

import static com.salesforce.eventmanager.api.common.EventManagerConstant.CREATE_CLIENT_TABLE;
import static com.salesforce.eventmanager.api.constants.EventManagerTestConstant.DUMMY_IP_ADDRESS;

public class ClientStatusRepositoryTest {
    private static Environment env;
    private static DBI dbi;
    private ClientStatusRepository clientStatusRepository;

    @BeforeAll
    public static void setup() {
        env = new Environment("test-env", Jackson.newObjectMapper(), null, new MetricRegistry(), null);
        dbi = new DBIFactory().build(env, getDataSourceFactory(), "test");
        dbi.useHandle(handle -> {
            handle.execute(CREATE_CLIENT_TABLE);
        });
    }

    @AfterAll
    public static void tearDown() {
        dbi.useHandle(handle -> handle.execute("drop table client_status"));
        dbi.useHandle(Handle::close);
    }

    @BeforeEach
    public void setUp() {
        clientStatusRepository = dbi.onDemand(ClientStatusRepository.class);
    }

    @Test
    public void testCreate() {
        clientStatusRepository.createOrUpdate(DUMMY_IP_ADDRESS, Status.AVAILABLE.name(), new Date());
        ClientStatus status = clientStatusRepository.findClientStatusById(DUMMY_IP_ADDRESS);

        Assertions.assertNotNull(status);
        Assertions.assertEquals(DUMMY_IP_ADDRESS, status.getClientIp());
        Assertions.assertEquals(Status.AVAILABLE, status.getStatus());
    }

    @Test
    public void testUpdate() {
        clientStatusRepository.createOrUpdate(DUMMY_IP_ADDRESS, Status.AVAILABLE.name(), new Date());
        clientStatusRepository.createOrUpdate(DUMMY_IP_ADDRESS, Status.TIMEOUT.name(), new Date());

        ClientStatus status = clientStatusRepository.findClientStatusById(DUMMY_IP_ADDRESS);

        Assertions.assertNotNull(status);
        Assertions.assertEquals(DUMMY_IP_ADDRESS, status.getClientIp());
        Assertions.assertEquals(Status.TIMEOUT, status.getStatus());
    }

    @Test
    public void testFindExpiredByStatus() {
        clientStatusRepository.createOrUpdate(DUMMY_IP_ADDRESS, Status.AVAILABLE.name(), new Date());

        List<ClientStatus> statusList = clientStatusRepository.findExpiredByStatus(Status.AVAILABLE.name(), new Date());

        Assertions.assertNotNull(statusList);
        Assertions.assertEquals(1, statusList.size());
    }

    static DataSourceFactory getDataSourceFactory() {
        DataSourceFactory dataSourceFactory = new DataSourceFactory();
        dataSourceFactory.setDriverClass("org.h2.Driver");
        dataSourceFactory.setUrl("jdbc:h2:mem:testDb;MODE=MySQL;MVCC=FALSE");
        return dataSourceFactory;
    }

    public static DBI getDbi() {
        return dbi;
    }

    public static Environment getEnvironment() {
        return env;
    }

}
