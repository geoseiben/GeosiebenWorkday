package com.geosieben.gsbworkday.repository;
import com.geosieben.gsbworkday.dto.EmployeeResponseDto;
import com.geosieben.gsbworkday.dto.StatsDto;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BasicInfoRepository extends JpaRepository<EmployeeBasicInfo,String> {


    @Query("SELECT new com.geosieben.gsbworkday.dto.EmployeeResponseDto(" +
            "e.EID, e.firstName, j.dateOfJoining, d.designation, " +
            "e.phone, e.email, j.status, j.workmail, " +
            "ed.fieldOfStudy, ed.yearOfPassing, e.dob) " +
            "FROM EmployeeBasicInfo e " +
            "JOIN EmployeeJoiningInfo j ON e.EID = j.EID " +
            "JOIN EmployeeDesignationInfo d ON d.EID = e.EID " +
            "JOIN EmployeeEducationInfo ed ON ed.EID = e.EID")
   List<EmployeeResponseDto> fetchAllEmployees();
    @Query("SELECT new com.geosieben.gsbworkday.dto.EmployeeResponseDto(" +
            "e.EID, e.firstName, j.dateOfJoining, d.designation, " +
            "e.phone, e.email, j.status, j.workmail, " +
            "ed.fieldOfStudy, ed.yearOfPassing, e.dob) " +
            "FROM EmployeeBasicInfo e " +
            "JOIN EmployeeJoiningInfo j ON e.EID = j.EID " +
            "JOIN EmployeeDesignationInfo d ON d.EID = e.EID " +
            "JOIN EmployeeEducationInfo ed ON ed.EID = e.EID where e.EID=:empid")
    EmployeeResponseDto fetchEmployee(@Param("empid") String eid);

@Query(nativeQuery = true, name = "EmployeeBasicInfo.fetchAggregates")
    StatsDto fetchAggregates();



    EmployeeBasicInfo findEmployeeBasicInfoByEID(String s);

}
