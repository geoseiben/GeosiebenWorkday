package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.geosieben.gsbworkday.dto.SeparationRequestsProjection;
import com.geosieben.gsbworkday.entity.Separation;

@Repository
public interface SeparationRepository extends JpaRepository<Separation,Integer> {

@Query(value = "SELECT s.separationid, s.ReasonforLeaving, s.resignationDate, s.separationType, s.status, " +
                "s.lastWorkingDay, e.firstName, e.lastName ,e.EID,j.dateOfJoining ,s.adminRemarks " +
                "FROM separation s " +
                "INNER JOIN employee_basic_info e ON s.eid = e.eid " +
            "INNER JOIN employee_joining_info j ON s.eid = j.eid " +
                "ORDER BY s.separationid DESC", 
       nativeQuery = true)
List<SeparationRequestsProjection> getSeparationRequests();
    
}
