package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.dto.PendingLeaveResponseDto;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.String;
import java.util.List;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link LeaveRepository}.
 */
@Generated
public class LeaveRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public LeaveRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link LeaveRepository#findPendingLeaves()}.
   */
  public List<PendingLeaveResponseDto> findPendingLeaves() {
    String queryString = "SELECT new com.geosieben.gsbworkday.dto.PendingLeaveResponseDto(l.id, CONCAT(e.firstName, ' ', e.lastName), e.EID, l.leaveType, l.Description, l.fromDate, l.toDate, l.NoofDays, l.fromsession, l.toSession) FROM Leaves l JOIN l.employeeBasicInfo e where l.status = 0";
    Query query = this.entityManager.createQuery(queryString);

    return (List<PendingLeaveResponseDto>) convertMany(query.getResultList(), false, PendingLeaveResponseDto.class);
  }
}
