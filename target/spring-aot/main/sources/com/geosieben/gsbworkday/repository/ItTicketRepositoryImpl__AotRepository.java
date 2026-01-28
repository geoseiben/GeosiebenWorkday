package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.dto.ActiveTickets;
import com.geosieben.gsbworkday.entity.ItTicket;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.String;
import java.util.List;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link ItTicketRepository}.
 */
@Generated
public class ItTicketRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public ItTicketRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link ItTicketRepository#activeTickets()}.
   */
  public List<ActiveTickets> activeTickets() {
    String queryString = "SELECT new com.geosieben.gsbworkday.dto.ActiveTickets(t.ticketid, t.issue, t.description, t.priority, t.hostname, t.anydeskid, concat(e.firstName, ' ', e.lastName), t.status) FROM ItTicket t LEFT JOIN EmployeeBasicInfo e ON t.EID = e.EID";
    Query query = this.entityManager.createQuery(queryString);

    return (List<ActiveTickets>) convertMany(query.getResultList(), false, ActiveTickets.class);
  }

  /**
   * AOT generated implementation of {@link ItTicketRepository#findByEmployeeBasicInfo_EID(java.lang.String)}.
   */
  public List<ItTicket> findByEmployeeBasicInfo_EID(String eid) {
    String queryString = "SELECT i FROM ItTicket i WHERE i.employeeBasicInfo.EID = :eid";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("eid", eid);

    return (List<ItTicket>) query.getResultList();
  }
}
