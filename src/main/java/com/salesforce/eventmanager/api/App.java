package com.salesforce.eventmanager.api;

import com.salesforce.eventmanager.api.common.EventType;
import com.salesforce.eventmanager.api.configuration.AppConfig;
import com.salesforce.eventmanager.api.exception.EventManagerException;
import com.salesforce.eventmanager.api.exception.EventManagerExceptionMapper;
import com.salesforce.eventmanager.api.listeners.ClientStatusListener;
import com.salesforce.eventmanager.api.publisher.EventManager;
import com.salesforce.eventmanager.api.repository.ClientStatusRepository;
import com.salesforce.eventmanager.api.resources.EventResource;
import com.salesforce.eventmanager.api.service.EventService;
import com.salesforce.eventmanager.api.task.ClientStatusUpdateTask;
import io.dropwizard.Application;
import io.dropwizard.jdbi.DBIFactory;
import io.dropwizard.setup.Environment;
import org.skife.jdbi.v2.DBI;

import java.util.Date;
import java.util.Timer;

import static com.salesforce.eventmanager.api.common.EventManagerConstant.CREATE_CLIENT_TABLE;

/**
 * @author Pushpendra Pal
 */
public class App extends Application<AppConfig> {
    public static void main(String[] args) throws Exception {
        App app = new App();
        app.run(args);
    }

    @Override
    public void run(AppConfig appConfig, Environment environment) throws Exception {
        final DBIFactory factory = new DBIFactory();
        final DBI jdbi = factory.build(environment, appConfig.getDataSourceFactory(), "postgresql");
        jdbi.useHandle(handle -> {
            handle.execute(CREATE_CLIENT_TABLE);
        });
        final ClientStatusRepository clientStatusRepository = jdbi.onDemand(ClientStatusRepository.class);
        final EventManager eventManager = new EventManager();
        eventManager.subscribe(EventType.PING, new ClientStatusListener(clientStatusRepository));
        eventManager.subscribe(EventType.TIMEOUT, new ClientStatusListener(clientStatusRepository));
        final EventService eventService = new EventService(clientStatusRepository, eventManager, appConfig);
        final EventResource eventResource = new EventResource(eventService, eventManager);
        environment.jersey().register(eventResource);
        environment.jersey().register(EventManagerExceptionMapper.class);
        final ClientStatusUpdateTask task = new ClientStatusUpdateTask(eventService, eventManager);
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(task, new Date(), appConfig.getTaskRunInterval());
    }
}
