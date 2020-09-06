package com.salesforce.eventmanager.api.configuration;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author Pushpendra Pal
 */
public class AppConfig extends Configuration {
    @Valid
    @NotNull
    @JsonProperty("database")
    private final DataSourceFactory database = new DataSourceFactory();

    @NotNull
    private long thresholdInSeconds;

    @NotNull
    private long taskRunInterval;


    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    public long getThresholdInSeconds() {
        return thresholdInSeconds;
    }

    public long getTaskRunInterval() {
        return taskRunInterval;
    }
}
