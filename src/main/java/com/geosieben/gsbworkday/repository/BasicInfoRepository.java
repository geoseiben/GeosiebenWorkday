package com.geosieben.gsbworkday.repository;
import com.geosieben.gsbworkday.dto.AttendanceResponseDto;
import com.geosieben.gsbworkday.dto.AvailableUsers;
import com.geosieben.gsbworkday.dto.EmployeeProfileProjection;
import com.geosieben.gsbworkday.dto.EmployeeReportProjection;
import com.geosieben.gsbworkday.dto.EmployeeResponseDto;
import com.geosieben.gsbworkday.dto.EmployeeStatsProjection;
import com.geosieben.gsbworkday.dto.ManageEmployeeResponseDto;
import com.geosieben.gsbworkday.dto.SeparatedEmployeeProjection;
import com.geosieben.gsbworkday.dto.StatsDto;
import com.geosieben.gsbworkday.dto.attendanceRecordDto;
import com.geosieben.gsbworkday.entity.EmployeeBasicInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BasicInfoRepository extends JpaRepository<EmployeeBasicInfo,String> {


    @Query("SELECT new com.geosieben.gsbworkday.dto.EmployeeResponseDto(" +
            "e.EID, concat(e.firstName,' ',e.lastName), j.dateOfJoining, d.designation, " +
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

    @Query(value = "SELECT e.* FROM employee_basic_info e "+
        "JOIN employee_joining_info j on e.eid=j.eid "+
        "LEFT JOIN salarystructure s on s.eid=e.eid "+
        " WHERE e.employmentStatus=1 "+
        " and e.eid not in(select eid from salarystructure )",nativeQuery = true)
    public List<EmployeeBasicInfo> payRollEmployee();
    
@Query(value = "SELECT e.* FROM employee_basic_info e "+
               "LEFT JOIN  salaryextradetails s ON e.eid=s.eid "+
               "WHERE e.EID not in(SELECT eid from salaryextradetails"+
               " where month=:month and lopindays>0) ",nativeQuery = true
)
public  List<EmployeeBasicInfo> getlopEmplpoyees(@Param("month") String month);

@Query(value = "SELECT e.* FROM employee_basic_info e "+
               "LEFT JOIN  salaryextradetails s ON e.eid=s.eid "+
               "WHERE e.EID not in(SELECT eid from salaryextradetails"+
               " where month=:month and extrapay>0) ",nativeQuery = true
)
public  List<EmployeeBasicInfo> getextrapayEmplpoyees(@Param("month") String month);

@Query(value = "SELECT e.EID,e.firstName,e.lastName,:date as date,a.leaveDays,a.leaveType,a.status from employee_basic_info e "+
    "left join attendance a on e.EID = a.EID and a.date=:date",nativeQuery = true
)
public List<AttendanceResponseDto>  getAttendanceData(@Param("date") String date);


@Query(value="SELECT e.firstName,e.lastName,a.id,e.eid,:date as attendanceDate,a.status, "+
    "a.workingDay,a.leaveType,a.leaveDays "+
    " FROM employee_basic_info e "+
    " LEFT JOIN attendance a on a.eid=e.eid and date=:date "+
    " WHERE e.employmentStatus<>0",nativeQuery = true
)
public List<attendanceRecordDto> getAllAttendanceData(@Param("date") String date);

@Query(value="SELECT e.eid,e.firstName,e.lastName,e.phone,e.email,e.dob,j.dateOfJoining,j.status,j.workmail,concat(m.firstName,' ',m.lastName) as manager,d.designation,td.departmentShortName,i.image,id.idIssued,"+
"id.issuedDate,0 as experienceYears,0 as experienceMonths,0 as experienceDays FROM employee_basic_info e"+
" LEFT JOIN employee_joining_info j on j.eid=e.eid "+
" LEFT JOIN employee_designation_info d on e.eid=d.eid "+
" LEFT JOIN employee_basic_info m on m.eid=j.reportingManager "+
" LEFT JOIN images i on i.eid=e.eid "+
"LEFT JOIN idcardissue id on e.eid=id.eid "+
" LEFT JOIN departments td on td.id=d.department",nativeQuery = true)
public List<ManageEmployeeResponseDto> getManageEmployeeList();

@Query(value = "select concat(e.firstName,' ',e.lastName) as name ,i.image as avatarUrl ,e.eid "+
    "FROM employee_basic_info e left join images i on i.eid=e.eid where e.eid<>:eid",nativeQuery = true
)
public List<AvailableUsers> getAvailableUsers(@Param("eid") String eid);

@Query (value = "select e.eid as employeeId,e.firstName,e.lastName,date_format(e.dob,'%d-%b-%Y') as dob,e.gender, "+
" e.maritalStatus,e.employmentStatus,e.bloodGroup,e.email as personalEmail,e.phone,e.address, "+
" date_format(j.dateOfJoining,'%d-%b-%Y') as dateOfJoining,j.workmail, "+
" j.status,date_format(j.confirmationDate,'%d-%b-%Y') as confirmationDate,d.designation,dt.departmentName, "+
" s.aadhar,s.pan,s.uan,s.pfNo,date_format(s.pfStartDate,'%d-%b-%Y') as pfStartDate,s.esiNo, "+
" ec.emergencyContactName,ec.emergencyContactNum,ec.relation as emergencyRelation,ee.college, "+
" b.bankName,b.branchName,b.accountNumber,ee.degree,ee.fieldOfStudy,ee.yearOfPassing,ee.gpa,concat(m.firstName,' ',e.lastName) as manager,i.image "+
" from employee_basic_info e  "+
" inner join employee_joining_info j on e.eid=j.eid "+
" inner join employee_designation_info d on d.eid=e.eid "+
" inner join employee_statutory_info s on s.eid=e.eid "+
" inner join employee_emergcontact_info ec on e.eid=ec.eid "+
" inner join employee_bank_info b on b.eid=e.eid "+
" inner join employee_education_info ee on e.eid=ee.eid "+
" LEFT join images i on i.eid=e.eid "+
" left join employee_basic_info m on m.eid=j.reportingManager "+
" left join departments dt on dt.id=d.department",nativeQuery = true)
public  List<EmployeeReportProjection> getEmployeeReport();

@Query (value = "select e.eid,concat(e.firstname,' ',e.lastname) as employeeName,e.phone,e.email, "+
" date_format(j.dateofjoining,'%d-%b-%Y') as dateofjoining,d.designation,dt.departmentShortName, "+
" concat(m.firstname,' ',m.lastname) as manager,date_format(s.lastworkingDay,'%d-%b-%Y') as lastWorkingDay, "+
" s.eligibleForReHire,s.reasonforLeaving,concat(ab.firstname,' ',ab.lastname) as approvedBy,i.image "+
" from  "+
" 	employee_basic_info e  "+
" 	left join employee_joining_info j on e.eid=j.eid "+
" LEFT JOIN images i on i.eid=e.eid "+
"   left join employee_designation_info d on d.eid=e.eid "+
"   left join departments dt on dt.id=d.department "+
"   left join employee_basic_info m on m.eid=j.reportingManager "+
"   left join separation s on s.eid=e.eid and s.status=3 "+
"   left join employee_basic_info ab on ab.eid=s.ApprovedBy "+
"   where e.employmentStatus=0",nativeQuery=true)
    public List<SeparatedEmployeeProjection> findSeparatedEmployees();

    @Query (value = """
        SELECT 
    (CASUALLEAVES + SICKLEAVES + EARNEDLEAVES + restrictedholidays) AS leaveBalance,
    CASUALLEAVES,SICKLEAVES,EARNEDLEAVES,restrictedholidays AS RISTRICTEDLEAVES,

    -- Active tasks
    (SELECT COUNT(*) 
     FROM projectallocation 
     WHERE status NOT IN (3,6,10) 
       AND (
            ((type IN ('QC','Prod','Pilot') AND allottedto=:eid) 
             OR (type='QC' AND qcAssinee=:eid))
           )
    ) AS activeTasks,

    -- Booking hours for current month
    (SELECT SUM(hoursBooked) 
     FROM bookedhours 
     WHERE MONTH(date)=MONTH(CURRENT_DATE) 
       AND YEAR(date)=YEAR(CURRENT_DATE) 
       AND eid=:eid
    ) AS bookingHrs,

    -- Pending IT tickets
    (SELECT COUNT(*) 
     FROM ittickets 
     WHERE EID=:eid
       AND status<>'Closed'
    ) AS pendingTickets,

    -- Client interactions this month
    (SELECT COUNT(*) 
     FROM clientinteraction 
     WHERE MONTH(createdAt)=MONTH(CURRENT_DATE) 
       AND YEAR(createdAt)=YEAR(CURRENT_DATE) 
       AND addedBy=:eid
    ) AS clientInteraction,
          (SELECT targethours 
        FROM employeetargethours 
        WHERE eid=:eid and 
        monthnum=month(current_date) and year=year(current_date))
    AS targethours,coalesce((SELECT SUM(hours) as extraHours from extrahours where eid=:eid),0)
    AS extraHours
    
FROM leavebalance 
WHERE eid=:eid


        """,nativeQuery = true)
        public  EmployeeStatsProjection getEmployeeStats(@Param("eid") String eid);

        @Query (value = "select e.eid as employeeId,e.firstName,e.lastName,date_format(e.dob,'%d-%b-%Y') as dob,e.gender, "+
" e.maritalStatus,e.employmentStatus,e.bloodGroup,e.email as personalEmail,e.phone,e.address, "+
" date_format(j.dateOfJoining,'%d-%b-%Y') as dateOfJoining,j.workmail, "+
" j.status,date_format(j.confirmationDate,'%d-%b-%Y') as confirmationDate,d.designation,dt.departmentName, "+
" s.aadhar,s.pan,s.uan,s.pfNo,date_format(s.pfStartDate,'%d-%b-%Y') as pfStartDate,s.esiNo, "+
" ec.emergencyContactName,ec.emergencyContactNum,ec.relation as emergencyRelation,ee.college, "+
" b.bankName,b.branchName,b.accountNumber,ee.degree,ee.fieldOfStudy,ee.yearOfPassing,ee.gpa,concat(m.firstName,' ',e.lastName) as manager,i.image "+
" from employee_basic_info e  "+
" inner join employee_joining_info j on e.eid=j.eid "+
" inner join employee_designation_info d on d.eid=e.eid "+
" inner join employee_statutory_info s on s.eid=e.eid "+
" inner join employee_emergcontact_info ec on e.eid=ec.eid "+
" inner join employee_bank_info b on b.eid=e.eid "+
" inner join employee_education_info ee on e.eid=ee.eid "+
" LEFT join images i on i.eid=e.eid "+
" left join employee_basic_info m on m.eid=j.reportingManager "+
" left join departments dt on dt.id=d.department where e.eid=:eid",nativeQuery = true)
public  EmployeeReportProjection getEmployeeReportByEid(@Param("eid") String eid);
}
