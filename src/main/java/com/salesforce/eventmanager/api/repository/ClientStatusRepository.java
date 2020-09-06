package com.salesforce.eventmanager.api.repository;

import com.salesforce.eventmanager.api.repository.dao.ClientStatus;
import com.salesforce.eventmanager.api.repository.mapper.ClientStatusMapper;
import org.skife.jdbi.v2.sqlobject.Bind;
import org.skife.jdbi.v2.sqlobject.SqlQuery;
import org.skife.jdbi.v2.sqlobject.SqlUpdate;
import org.skife.jdbi.v2.sqlobject.customizers.Mapper;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @author Pushpendra Pal
 */
public abstract class ClientStatusRepository {
    @SqlUpdate("insert into client_status (id, status, status_date) values (:id, :status, :status_date)")
    public abstract void insert(@Bind("id") String id, @Bind("status") String status, @Bind("status_date") Date statusDate);

    @SqlUpdate("update client_status set status = :status, status_date =:status_date where id = :id")
    public abstract void update(@Bind("id") String id, @Bind("status") String status, @Bind("status_date") Date statusDate);

    @SqlQuery("select id, status, status_date from client_status where id = :id")
    @Mapper(ClientStatusMapper.class)
    public abstract ClientStatus findClientStatusById(@Bind("id") String id);

    @SqlQuery("select id, status, status_date from client_status where status = :status and status_date < :expiryDateTime limit 100")
    @Mapper(ClientStatusMapper.class)
    public abstract List<ClientStatus> findExpiredByStatus(@Bind("status") String status, @Bind("expiryDateTime") Date expiryDateTime);

    public void createOrUpdate(String id, String status, Date statusDate) {
        if (Objects.isNull(findClientStatusById(id))) {
            insert(id, status, statusDate);
        } else {
            update(id, status, statusDate);
        }
    }
}
