package com.geosieben.gsbworkday.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.geosieben.gsbworkday.entity.ProjectAllocation;

@Repository
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
Optional<ProjectAllocation> getRunningTasks(@Param("allottedto") String allottedto);}
