package com.geosieben.gsbworkday.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.AllotmentsProjection;
import com.geosieben.gsbworkday.dto.AssignedTaskProjection;
import com.geosieben.gsbworkday.entity.ProjectAllocation;

public interface AllocationRepository extends JpaRepository<ProjectAllocation ,Integer>{
@Query(value = "SELECT * FROM projectallocation WHERE pid = ?1", nativeQuery = true)
List<ProjectAllocation> findByProjectPid(int pid);
@Query(
  value = "select * from projectallocation " +
          "where ((type='Prod' or type='Pilot') and allottedto=?1 and status not in (3,10)) " +
          "or (type='QC' and qcAssinee=?2 and status not in (6,7))",
  nativeQuery = true
)
List<ProjectAllocation> myAllotments(String eid, String eid1);
@Query(value = "SELECT * FROM projectallocation  WHERE feederAlloted = :feeder AND pid = :pid AND type = 'Prod'",nativeQuery = true)
Optional<ProjectAllocation> findRelatedProdTask(String feeder, int pid);

@Query(value="SELECT * FROM projectallocation  WHERE feederAlloted = :feeder AND type = 'Prod' AND qcAssinee IS NOT NULL AND pid = :pid",nativeQuery=true)
Optional<ProjectAllocation> findProdByFeederAndPid(String feeder, int pid); 
@Query(value="SELECT * FROM projectallocation  WHERE (((type='Prod'OR type='Pilot') AND allottedto=:allottedto) OR ((type='QC') AND qcAssinee=:allottedto)) and isRunning=1",nativeQuery=true)
Optional<ProjectAllocation> getRunningTasks(@Param("allottedto") String allottedto);
@Query(value = "SELECT * FROM projectallocation WHERE feederAlloted = :feeder AND pid = :pid AND type = :type and qcAssignee=:qcAssignee LIMIT 1", nativeQuery = true)
ProjectAllocation getQcTask(
    @Param("feeder") String feeder, 
    @Param("pid") int pid, 
    @Param("type") String type,
    @Param("qcAssignee") String qcAssignee
);

@Query(value="SELECT pa.allotmentid,COALESCE(b.completionPercentage,0) as completionPercentage,pa.isRunning,pa.type,pa.deadline, "+
"pa.qcDeadline,pa.feederAlloted,pa.elapsedTime,pa.startTime,"+
"pa.hrsAssigned,pa.qcHrs FROM projectallocation pa "+
"LEFT JOIN (select allotmentid,max(completionpercentage) as completionPercentage FROM bookedhours GROUP BY  allotmentid) b on b.allotmentid =pa.allotmentid "+
"WHERE ((pa.type='Prod' OR pa.type='Pilot') AND pa.allottedto=:eid AND pa.status NOT IN (3,10)) "+
"OR (pa.type='QC' AND pa.qcAssinee=:eid and pa.status not in (6,7))",nativeQuery = true)
public List<AllotmentsProjection> getMyAllotments(@Param("eid") String eid);

@Query(value = """
  SELECT a.feederAlloted,
	CASE 
    WHEN a.type='Prod' or a.type='Pilot' then date_format(a.deadline,'%d-%b-%Y') 
    when a.type='QC' then date_format(a.qcDeadline,'%d-%b-%Y') end as  deadline,
    case a.status
		when 1 then 'Assigned'
        when 2 then 'In-Progress'
        when 4 then 'Qc Assigned'
        when 5 then 'QC In-Progress'
        end as status,
        coalesce(b.completionPercentage,0) as completionPercentage,
        coalesce(b.hours,0) as hours,
        	CASE 
    WHEN a.type='Prod' or a.type='Pilot' then a.hrsAssigned
    when a.type='QC' then a.qcHrs end as hrsAssigned,
    a.type
        
 FROM projectallocation  a
 
left join (select allotmentid,eid,type,
max(completionPercentage) as completionPercentage,sum(hoursbooked) as hours
from bookedhours group by allotmentid,eid) b on b.allotmentid=a.allotmentid 
WHERE (((a.type='Prod' or a.type='Pilot' OR a.type='QC') AND a.allottedto = :eid
AND a.status IN (1, 8)) OR (a.type='QC' AND a.qcAssinee = :eid AND a.status IN (4, 5))) 
  """,nativeQuery = true)
  public List<AssignedTaskProjection> getAssignedTasks(@Param("eid") String eid);

@Modifying(clearAutomatically = true, flushAutomatically = true)
@Query(
    value = "UPDATE projectallocation SET isDelivered = :delStatus WHERE feederAlloted = :feeder AND qcHrs IS NOT NULL AND pid = :pid",
    nativeQuery = true
)
int deliveryTask(@Param("delStatus") int status, @Param("feeder") String feeder, @Param("pid") int pid);
 }
 

