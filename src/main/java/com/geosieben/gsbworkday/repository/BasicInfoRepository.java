package com.geosieben.gsbworkday.repository;
import com.geosieben.gsbworkday.dto.EmployeeProfileProjection;
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

@Query(value = "SELECT e.firstName, e.lastName, e.gender, e.address, e.Eid, e.email, " +
               "j.dateOfJoining, j.status, j.workmail, " + // Added comma and space
               "s.pan, s.aadhar, s.uan, s.pfStartDate, " +
               "c.emergencyContactName, c.emergencyContactNum, c.relation, " + // Added comma
               "b.accountNumber, b.bankName, d.designation " +
               "FROM employee_basic_info e " +
               "JOIN employee_joining_info j ON e.EID = j.EID " +
               "JOIN employee_designation_info d ON e.EID = d.EID " +
               "JOIN employee_bank_info b ON e.EID = b.EID " +
               "JOIN employee_statutory_info s ON e.EID = s.EID " +
               "JOIN employee_emergcontact_info c ON e.EID = c.EID " +
               "WHERE e.EID = :empid", nativeQuery = true)
EmployeeProfileProjection employeeProfile(@Param("empid") String eid);


    EmployeeBasicInfo findEmployeeBasicInfoByEID(String s);

}
