package com.salesforce.eventmanager.api.repository.mapper;

import com.salesforce.eventmanager.api.common.Status;
import com.salesforce.eventmanager.api.repository.dao.ClientStatus;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.tweak.ResultSetMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author Pushpendra Pal
 */
public class ClientStatusMapper implements ResultSetMapper<ClientStatus> {
    @Override
    public ClientStatus map(int i, ResultSet resultSet, StatementContext statementContext) throws SQLException {
        ClientStatus clientStatus = new ClientStatus(resultSet.getString("id"));
        clientStatus.setStatus(Status.valueOf(resultSet.getString("status")));
        clientStatus.setDate(resultSet.getTimestamp("status_date"));
        return clientStatus;
    }
}
