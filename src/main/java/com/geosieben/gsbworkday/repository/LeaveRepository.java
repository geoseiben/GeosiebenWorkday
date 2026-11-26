package com.geosieben.gsbworkday.repository;

import com.geosieben.gsbworkday.dto.LeaveAvailedDto;
import com.geosieben.gsbworkday.dto.LeaveAvailedProj;
import com.geosieben.gsbworkday.dto.LeaveAvailedProjMonth;
import com.geosieben.gsbworkday.dto.LeaveDetailProjection;
import com.geosieben.gsbworkday.dto.LeaveProjection;
import com.geosieben.gsbworkday.dto.PendingLeaveResponseDto;
import com.geosieben.gsbworkday.entity.Leaves;

import org.antlr.v4.runtime.atn.SemanticContext.AND;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaveRepository extends JpaRepository<Leaves,Integer> {
    @Query("SELECT new com.geosieben.gsbworkday.dto.PendingLeaveResponseDto(" +
            "l.id, CONCAT(e.firstName, ' ', e.lastName), e.EID, " +
            "l.leaveType, l.Description, l.fromDate, " +
            " l.toDate,l.NoofDays, l.fromsession, l.toSession) " +
            "FROM Leaves l JOIN l.employeeBasicInfo e where l.status=0")
    List<PendingLeaveResponseDto> findPendingLeaves();

    @Query(value="SELECT * FROM tblleaves where status=1 order by id desc",nativeQuery = true)
    List<Leaves> findApprovedLeaves();

@Query (value = "SELECT CONCAT(e.firstname, ' ', e.lastname) AS employeeName,"+
            " l.EID,YEAR(l.FromDate) AS year,"+
            " SUM(CASE WHEN l.leaveType = 'CL' THEN l.noOfDays ELSE 0 END) AS clAvailed,"+
            " SUM(CASE WHEN l.leaveType = 'SL' THEN l.noOfDays ELSE 0 END) AS sLAvailed,"+
            " SUM(CASE WHEN l.leaveType = 'RH' THEN l.noOfDays ELSE 0 END) AS rhAvailed,"+
            " SUM(CASE WHEN l.leaveType = 'EL' THEN l.noOfDays ELSE 0 END) AS elAvailed,"+
            " SUM(CASE WHEN l.leaveType = 'CO' THEN l.noOfDays ELSE 0 END) AS coAvailed "+
       " FROM tblleaves l "+
       " INNER JOIN employee_basic_info e ON e.eid = l.EID "+
       " WHERE l.status = 1 "+
       " GROUP BY employeeName, year, l.EID "+
       " ORDER BY l.EID ASC, year DESC",nativeQuery = true)
       public  List<LeaveAvailedProj> getAvailedReport();

@Query(value = "WITH RECURSIVE LeaveDays AS ( " +
        " SELECT EID, leaveType, fromDate AS current_leave_date, " +
        " fromDate, toDate, fromSession, toSession " +
        " FROM tblleaves " +
        " WHERE status = 1 AND EID = :eid AND YEAR(fromDate) <= :year AND YEAR(toDate) >= :year " +
        " UNION ALL " +
        " SELECT EID, leaveType, DATE_ADD(current_leave_date, INTERVAL 1 DAY), " +
        " fromDate, toDate, fromSession, toSession " +
        " FROM LeaveDays " +
        " WHERE current_leave_date < toDate " +
        "), " +
        "DailyLeaves AS ( " +
        " SELECT EID, leaveType, current_leave_date, " +
        " CASE " +
        "   WHEN current_leave_date = fromDate AND current_leave_date = toDate THEN " +
        "     CASE " +
        "       WHEN fromSession = 'Session 1' AND toSession = 'Session 1' THEN 0.5 " +
        "       WHEN fromSession = 'Session 2' AND toSession = 'Session 2' THEN 0.5 ELSE 1.0 END " +
        "   WHEN current_leave_date = fromDate THEN " +
        "     CASE " +
        "       WHEN fromSession = 'Session 2' THEN 0.5 ELSE 1.0 END " +
        "   WHEN current_leave_date = toDate THEN " +
        "     CASE " +
        "       WHEN toSession = 'Session 1' THEN 0.5 ELSE 1.0 END " +
        "   ELSE 1.0 END AS day_weight " +
        " FROM LeaveDays " +
        " WHERE YEAR(current_leave_date) = :year " +
        ") " +
        "SELECT " +
        " DATE_FORMAT(current_leave_date, '%b-%Y') AS month, " +
        " SUM(CASE WHEN leaveType = 'CL' THEN day_weight ELSE 0 END) AS clAvailed, " +
        " SUM(CASE WHEN leaveType = 'SL' THEN day_weight ELSE 0 END) AS SLAvailed, " +
        " SUM(CASE WHEN leaveType = 'RH' THEN day_weight ELSE 0 END) AS rhAvailed, " +
        " SUM(CASE WHEN leaveType = 'EL' THEN day_weight ELSE 0 END) AS elAvailed, " +
        " SUM(CASE WHEN leaveType = 'CO' THEN day_weight ELSE 0 END) AS coAvailed " +
        "FROM DailyLeaves " +
        "GROUP BY DATE_FORMAT(current_leave_date, '%b-%Y'), DATE_FORMAT(current_leave_date, '%Y-%m') " +
        "ORDER BY DATE_FORMAT(current_leave_date, '%Y-%m') ASC", nativeQuery = true)
public List<LeaveAvailedProjMonth> getLeaveAvailedByMonthForYear(@Param("year") int year, @Param("eid") String eid);


@Query(value = """
            SELECT
                l.id,
                ROUND(l.NoofDays, 1) AS NoofDays,
                l.FromDate,
                l.ToDate,
                l.LeaveType,
                l.fromsession,
                l.tosession,
                l.Description,
                l.eid,
                DATE_FORMAT(l.fromdate, '%d-%b-%Y') AS fromdatee,
                DATE_FORMAT(l.todate, '%d-%b-%Y') AS todatee,
                j.reportingmanager,
                CONCAT(e.firstname, ' ', e.lastname) AS employeeName,
                CONCAT(m.firstname, ' ', m.lastname) AS manager,
                DATE_FORMAT(l.postingdate, '%d-%b-%Y') AS appliedOn,
                DATE_FORMAT(l.AdminRemarkDate, '%d-%b-%Y') AS approvedOn,
                CONCAT(ab.firstname, ' ', ab.lastname) AS approvedBy,
                'N/A' AS department,
                l.status,
                CASE
                    WHEN co.compOffDate IS NOT NULL AND co.workedDate IS NULL THEN 'Pending'
                    WHEN co.compOffDate IS NOT NULL AND co.workedDate IS NOT NULL THEN 'Cleared'
                    ELSE NULL
                END AS coStatus
            FROM tblleaves l
            INNER JOIN employee_basic_info e ON l.eid = e.eid
            INNER JOIN employee_joining_info j ON e.eid = j.eid
            LEFT JOIN employee_basic_info m ON j.ReportingManager = m.eid
            LEFT JOIN employee_basic_info ab ON l.ApprovedBy = ab.eid
            LEFT JOIN compensatoryoff co ON co.lid = l.id
            WHERE l.status = 1
            ORDER BY l.eid ASC
            """, nativeQuery = true)
        public List<LeaveProjection> findLeavesForCalenderProjection();

        @Query(value = """
            SELECT 
                id AS id, 
                eid AS eid, 
                DATE_FORMAT(fromdate, '%d-%b-%Y') AS fromDate,
                DATE_FORMAT(toDate, '%d-%b-%Y') AS toDate,
                noofdays AS noofdays, 
                description AS description, 
                CASE leaveType 
                    WHEN 'CL' THEN 'Casual Leave'
                    WHEN 'SL' THEN 'Sick Leave'
                    WHEN 'EL' THEN 'Earned Leave'
                    WHEN 'RH' THEN 'Restricted Holiday'
                    WHEN 'CO' THEN 'Compensatory Off'
                END AS leaveType, 
                CASE status
                    WHEN 0 THEN 'Pending'
                    WHEN 1 THEN 'Approved'
                    WHEN 2 THEN 'Rejected'
                    WHEN 3 THEN 'Cancelled'
                    WHEN 4 THEN 'Withdrawn'
                    WHEN 5 THEN 'Hold'
                END AS status
            FROM tblleaves 
            WHERE EID = :eid 
            ORDER BY id DESC
            """, nativeQuery = true)
  public   List<LeaveDetailProjection> findLeavesByEmployeeId(@Param("eid") String eid);
}

