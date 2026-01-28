package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.dto.LeaveBalanceReposnseDto;
import com.geosieben.gsbworkday.entity.LeaveBalance;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.String;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link LeaveBalanceRepository}.
 */
@Generated
public class LeaveBalanceRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public LeaveBalanceRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link LeaveBalanceRepository#findByEmployeeBasicInfo_EID(java.lang.String)}.
   */
  public LeaveBalance findByEmployeeBasicInfo_EID(String eid) {
    String queryString = "SELECT l FROM LeaveBalance l WHERE l.employeeBasicInfo.EID = :eid";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("eid", eid);

    return (LeaveBalance) convertOne(query.getSingleResultOrNull(), false, LeaveBalance.class);
  }

  /**
   * AOT generated implementation of {@link LeaveBalanceRepository#findLeaveBalanceByEid(java.lang.String)}.
   */
  public LeaveBalanceReposnseDto findLeaveBalanceByEid(String eid) {
    String queryString = "SELECT new com.geosieben.gsbworkday.dto.LeaveBalanceReposnseDto(e.EID, CONCAT(e.firstName, ' ', e.lastName), lb.casualleaves, lb.sickleaves, lb.restrictedholidays, lb.earnedleaves) FROM LeaveBalance lb JOIN lb.employeeBasicInfo e WHERE e.EID = :eid";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("eid", eid);

    return (LeaveBalanceReposnseDto) convertOne(query.getSingleResultOrNull(), false, LeaveBalanceReposnseDto.class);
  }
}
