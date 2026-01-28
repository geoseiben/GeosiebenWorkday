package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.dto.EmployeeResponseDto;
import com.geosieben.gsbworkday.dto.StatsDto;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.lang.String;
import java.util.List;
import org.springframework.aot.generate.Generated;
import org.springframework.data.jpa.repository.aot.AotRepositoryFragmentSupport;
import org.springframework.data.jpa.repository.query.QueryEnhancerSelector;
import org.springframework.data.repository.core.support.RepositoryFactoryBeanSupport;

/**
 * AOT generated JPA repository implementation for {@link BasicInfoRepository}.
 */
@Generated
public class BasicInfoRepositoryImpl__AotRepository extends AotRepositoryFragmentSupport {
  private final RepositoryFactoryBeanSupport.FragmentCreationContext context;

  private final EntityManager entityManager;

  public BasicInfoRepositoryImpl__AotRepository(EntityManager entityManager,
      RepositoryFactoryBeanSupport.FragmentCreationContext context) {
    super(QueryEnhancerSelector.DEFAULT_SELECTOR, context);
    this.entityManager = entityManager;
    this.context = context;
  }

  /**
   * AOT generated implementation of {@link BasicInfoRepository#fetchAggregates()}.
   */
  public StatsDto fetchAggregates() {
    String queryString = "SELECT  (SELECT COUNT(*) FROM employee_basic_info e WHERE e.employmentstatus = 1) AS totalActiveEmployees,  (SELECT COUNT(*) FROM employee_basic_info e WHERE e.employmentstatus = 1 AND e.gender = 'Male') AS activeMale,  (SELECT COUNT(*) FROM employee_basic_info e WHERE e.employmentstatus = 1 AND e.gender = 'Female') AS activeFemale,  (SELECT COUNT(*) FROM employee_basic_info e WHERE e.employmentstatus = 0) AS totalSeparatedEmployees,  (SELECT COUNT(*) FROM employee_basic_info e WHERE e.employmentstatus = 0 AND e.gender = 'Male') AS separatedMale,  (SELECT COUNT(*) FROM employee_basic_info e WHERE e.employmentstatus = 0 AND e.gender = 'Female') AS separatedFemale,  (SELECT COUNT(*) FROM tblleaves l WHERE l.status = 0) AS pendingLeaves,  (SELECT COUNT(*) FROM ittickets i WHERE i.status < 2) AS pendingTickets";
    Query query = this.entityManager.createNativeQuery(queryString, StatsDto.class);

    return (StatsDto) convertOne(query.getSingleResultOrNull(), true, StatsDto.class);
  }

  /**
   * AOT generated implementation of {@link BasicInfoRepository#fetchAllEmployees()}.
   */
  public List<EmployeeResponseDto> fetchAllEmployees() {
    String queryString = "SELECT new com.geosieben.gsbworkday.dto.EmployeeResponseDto(e.EID, e.firstName, j.dateOfJoining, d.designation, e.phone, e.email, j.status, j.workmail, ed.fieldOfStudy, ed.yearOfPassing, e.dob) FROM EmployeeBasicInfo e JOIN EmployeeJoiningInfo j ON e.EID = j.EID JOIN EmployeeDesignationInfo d ON d.EID = e.EID JOIN EmployeeEducationInfo ed ON ed.EID = e.EID";
    Query query = this.entityManager.createQuery(queryString);

    return (List<EmployeeResponseDto>) convertMany(query.getResultList(), false, EmployeeResponseDto.class);
  }

  /**
   * AOT generated implementation of {@link BasicInfoRepository#fetchEmployee(java.lang.String)}.
   */
  public EmployeeResponseDto fetchEmployee(String eid) {
    String queryString = "SELECT new com.geosieben.gsbworkday.dto.EmployeeResponseDto(e.EID, e.firstName, j.dateOfJoining, d.designation, e.phone, e.email, j.status, j.workmail, ed.fieldOfStudy, ed.yearOfPassing, e.dob) FROM EmployeeBasicInfo e JOIN EmployeeJoiningInfo j ON e.EID = j.EID JOIN EmployeeDesignationInfo d ON d.EID = e.EID JOIN EmployeeEducationInfo ed ON ed.EID = e.EID where e.EID = :empid";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("empid", eid);

    return (EmployeeResponseDto) convertOne(query.getSingleResultOrNull(), false, EmployeeResponseDto.class);
  }

  /**
   * AOT generated implementation of {@link BasicInfoRepository#findEmployeeBasicInfoByEID(java.lang.String)}.
   */
  public EmployeeBasicInfo findEmployeeBasicInfoByEID(String s) {
    String queryString = "SELECT e FROM EmployeeBasicInfo e WHERE e.EID = :s";
    Query query = this.entityManager.createQuery(queryString);
    query.setParameter("s", s);

    return (EmployeeBasicInfo) convertOne(query.getSingleResultOrNull(), false, EmployeeBasicInfo.class);
  }
}
