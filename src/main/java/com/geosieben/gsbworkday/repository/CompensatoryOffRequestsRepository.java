package com.geosieben.gsbworkday.repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.CoRequestProjection;
import com.geosieben.gsbworkday.entity.CompensatoryOffRequests;

public interface CompensatoryOffRequestsRepository extends JpaRepository<CompensatoryOffRequests,Integer>{
@Query(value="SELECT * FROM compoffrequests where workeddate=:date and requester=:requester and status=0",nativeQuery=true)
public Optional<CompensatoryOffRequests> findPendingRequest(@Param("date") LocalDate workDate,@Param("requester") String eid);

@Query(value="SELECT id,date_format(compOffDate,'%d-%b-%Y') as compOffDate,date_format(workedDate,'%d-%b-%Y') as workedDate,"+
"requestType,remarks,status FROM compoffrequests where requester=:eid order by id desc",nativeQuery = true)
public List<CoRequestProjection> getMyCoRequests(@Param("eid") String eid);


@Query(value = "SELECT c.id, " +
               "CONCAT(e.firstname, ' ', e.lastname) AS requester, " +
               "CONCAT(a.firstname, ' ', a.lastname) AS actionBy, " +
               "CONCAT(DATE_FORMAT(c.compOffDate, '%d-%b-%Y'), '(', DAYNAME(c.compOffDate), ')') AS compOffDate, " +
               "CONCAT(DATE_FORMAT(c.workedDate, '%d-%b-%Y'), '(', DAYNAME(c.workedDate), ')') AS workedDate, " +
               "DATE_FORMAT(c.requestedOn, '%d-%b-%Y') AS requestedOn, " +
               "c.remarks, c.requestType, " +
               "CASE c.status " +
               "  WHEN 0 THEN 'Pending' " +
               "  WHEN 1 THEN 'Approved' " +
               "  WHEN 2 THEN 'Rejected' " +
               "END AS status " +
               "FROM compoffrequests c " +
               "JOIN employee_basic_info e ON c.requester = e.EID " +
               "LEFT JOIN employee_basic_info a ON a.EID = c.actionBy " +
               "ORDER BY c.id DESC", nativeQuery = true)
List<CoRequestProjection> getRequests();

}
