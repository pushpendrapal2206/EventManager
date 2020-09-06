package com.salesforce.eventmanager.api.repository.dao;

import com.salesforce.eventmanager.api.common.Status;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Pushpendra Pal
 */
public class ClientStatus implements Serializable {
    private static final long serialVersionUID = 1L;
    private String clientIp;
    private Status status;
    private Date date;

    public ClientStatus(String clientIp) {
        this.clientIp = clientIp;
    }

    public String getClientIp() {
        return clientIp;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
