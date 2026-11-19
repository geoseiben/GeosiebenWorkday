package com.geosieben.gsbworkday.repository;

import java.util.List;
import java.util.Optional;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.antlr.v4.runtime.atn.SemanticContext.OR;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.AttendenceMasterReportProjection;
import com.geosieben.gsbworkday.dto.MonthProjection;
import com.geosieben.gsbworkday.entity.Attendance;


public interface AttendanceRepository extends JpaRepository<Attendance,Integer> {
@Query(value="SELECT * FROM attendance where date=:date and eid=:eid",nativeQuery = true)
public Optional<Attendance> findExistingAttendanceRecord(@Param("date") String date,@Param("eid") String eid);

@Query(value="SELECT * FROM attendance where date=:date",nativeQuery = true)
public List<Attendance> findAllByDate(@Param("date") String date);
@Query(value = "SELECT " +
        "    DATE_FORMAT(date, '%b-%Y') AS month," +
        "    COUNT(*) AS total " +
        " FROM attendance " +
        " GROUP BY DATE_FORMAT(date, '%b-%Y') " + 
        " ORDER BY MAX(date) DESC",nativeQuery =  true)
public  List<MonthProjection> getAttendancemonths();
@Query(value = "WITH RECURSIVE TargetMonth AS ( " +
    "    SELECT STR_TO_DATE(CONCAT('01-', :month), '%d-%b-%Y') AS start_date " +
    "), " +
    "MonthDays AS ( " +
    "    SELECT start_date AS reportDate FROM TargetMonth " +
    "    UNION ALL " +
    "    SELECT reportDate + INTERVAL 1 DAY " +
    "    FROM MonthDays " +
    "    WHERE reportDate < ( " +
    "        CASE " +
    "            WHEN DATE_FORMAT((SELECT start_date FROM TargetMonth), '%Y-%m') = DATE_FORMAT(CURRENT_DATE(), '%Y-%m') " +
    "            THEN CURRENT_DATE() " +
    "            ELSE LAST_DAY((SELECT start_date FROM TargetMonth)) " +
    "        END " +
    "    ) " +
    "), " +
    "ActiveEmployees AS ( " +
    "    SELECT " +
    "        e.EID, " +
    "        CONCAT(e.firstname, ' ', e.lastname) AS employeeName, " +
    "        j.DateOfJoining, " +
    "        s.LastWorkingDay " +
    "    FROM employee_basic_info e " +
    "    INNER JOIN employee_joining_info j ON e.eid = j.eid " +
    "    LEFT JOIN separation s ON s.EID = e.eid " +
    "    WHERE " +
    "        (e.employmentStatus = 1 AND j.DateOfJoining <= LAST_DAY((SELECT start_date FROM TargetMonth))) " +
    "        OR " +
    "        (e.employmentStatus = 0 AND s.status = 'Completed' AND s.LastWorkingDay >= (SELECT start_date FROM TargetMonth)) " +
    ") " +
    "SELECT " +
    "    emp.EID, " +
    "    emp.employeeName, " +
    "    DATE_FORMAT(md.reportDate, '%d-%b-%y') AS attendanceDate, " +
    "    CASE " +
    "        WHEN md.reportDate < emp.DateOfJoining THEN 'Not Joined' " +
    "        WHEN emp.LastWorkingDay IS NOT NULL AND md.reportDate > emp.LastWorkingDay THEN 'Separated' " +
    "        WHEN a.status IN ('Present', 'Absent', 'Leave', 'Half Day', 'Leave/Present', 'Present/Leave', 'Leave/Absent', 'Absent/Leave') THEN a.status " +
    "        WHEN DAYOFWEEK(md.reportDate) = 1 THEN 'SUN' " +
    "        WHEN DAYOFWEEK(md.reportDate) = 7 AND (DAYOFMONTH(md.reportDate) BETWEEN 8 AND 14 OR md.reportDate >= LAST_DAY(md.reportDate) - INTERVAL 6 DAY) THEN 'Sat' " +
    "        ELSE 'UM' " +
    "    END AS status, " +
    "    a.workingDay, " +
    "    a.leaveDays, " +
    "    i.image, " +
    "    l.leaveType, " +
    "    a.leaveType AS lt " +
    "FROM MonthDays md " +
    "CROSS JOIN ActiveEmployees emp " +
    "LEFT JOIN images i ON i.eid = emp.EID " +
    "LEFT JOIN attendance a ON a.eid = emp.EID AND a.date = md.reportDate " +
    "LEFT JOIN tblleaves l ON l.eid = emp.eid AND md.reportDate BETWEEN l.fromdate AND l.todate AND l.status = 1 " +
    "ORDER BY emp.EID ASC, md.reportDate ASC;", nativeQuery = true)
public List<AttendenceMasterReportProjection> getMasterData(@Param("month") String month);
}
