package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.BookeHoursProjection;
import com.geosieben.gsbworkday.dto.ConsolidatedReportProjection;
import com.geosieben.gsbworkday.dto.DayWiseHoursProjection;
import com.geosieben.gsbworkday.dto.EmployeeAttendanceProjection;
import com.geosieben.gsbworkday.dto.MonthlyHoursProjection;
import com.geosieben.gsbworkday.dto.ProjectHoursProjection;
import com.geosieben.gsbworkday.entity.BookedHours;


public interface BookedHoursRepository extends JpaRepository<BookedHours,Integer>{
    @Query(value = "select * from bookedhours where EID=:eid and allotmentid=:allotmentid and date=current_date",nativeQuery = true)
    BookedHours findbydateAndAllotmentforToday(@Param("eid") String eid,@Param("allotmentid") int allotmentid );
    @Query(value = "select * from bookedhours where EID=:eid and allotmentid=:allotmentid order by id desc limit 1",nativeQuery = true)
    BookedHours findbyAndAllotmentforBookedSoFar(@Param("eid") String eid,@Param("allotmentid") int allotmentid );
@Query(value="SELECT e.firstName as firstName, e.lastName as lastName,e.eid, " +
             "SUM(CASE WHEN b.type = 'Prod' THEN b.hoursbooked ELSE 0 END) as prodHours, " +
             "SUM(CASE WHEN b.type = 'QC' THEN b.hoursbooked ELSE 0 END) as qcHours, " +
             "SUM(CASE WHEN b.type = 'Delivery' THEN b.hoursbooked ELSE 0 END) as deliveryHours, " +
             "SUM(CASE WHEN b.type = 'Pilot' THEN b.hoursbooked ELSE 0 END) as pilotHours, " +
             "SUM(b.hoursbooked) as totalHours " +
             "FROM bookedhours b INNER JOIN employee_basic_info e ON e.eid = b.eid " +
             "GROUP BY e.firstName, e.lastName,e.eid",
       nativeQuery = true)
List<BookeHoursProjection> getHoursByEmployeePivot();
@Query(value="SELECT p.allotmentName, " +
             "SUM(CASE WHEN b.type = 'Prod' THEN b.hoursbooked ELSE 0 END) as prodHours, " +
             "SUM(CASE WHEN b.type = 'QC' THEN b.hoursbooked ELSE 0 END) as qcHours, " +
             "SUM(CASE WHEN b.type = 'Delivery' THEN b.hoursbooked ELSE 0 END) as deliveryHours, " +
             "SUM(CASE WHEN b.type = 'Pilot' THEN b.hoursbooked ELSE 0 END) as pilotHours, " +
             "SUM(b.hoursbooked) as totalHours " +
             "FROM bookedhours b INNER JOIN projectallocation a on b.allotmentid=a.allotmentid " +
             "inner join  projects p on p.pid=a.pid "+
             "GROUP BY p.allotmentName",
       nativeQuery = true)
List<ProjectHoursProjection> getHoursByProjects();
@Query(value="SELECT date_format(b.date,'%b-%Y') as monthName, " +
             "SUM(CASE WHEN b.type = 'Prod' THEN b.hoursbooked ELSE 0 END) as prodHours, " +
             "SUM(CASE WHEN b.type = 'QC' THEN b.hoursbooked ELSE 0 END) as qcHours, " +
             "SUM(CASE WHEN b.type = 'Delivery' THEN b.hoursbooked ELSE 0 END) as deliveryHours, " +
             "SUM(CASE WHEN b.type = 'Pilot' THEN b.hoursbooked ELSE 0 END) as pilotHours, " +
             "SUM(b.hoursbooked) as totalHours " +
             "FROM bookedhours b group by date_format(b.date,'%b-%Y')",
       nativeQuery = true)
List<MonthlyHoursProjection> getHoursByMonthly();
@Query(value = "SELECT date_format(b.date, '%b-%Y') as monthName, " +
               "SUM(CASE WHEN b.type = 'Prod' THEN b.hoursbooked ELSE 0 END) as prodHours, " +
               "SUM(CASE WHEN b.type = 'QC' THEN b.hoursbooked ELSE 0 END) as qcHours, " +
               "SUM(CASE WHEN b.type = 'Delivery' THEN b.hoursbooked ELSE 0 END) as deliveryHours, " +
               "SUM(CASE WHEN b.type = 'Pilot' THEN b.hoursbooked ELSE 0 END) as pilotHours, " +
               "SUM(b.hoursbooked) as totalHours " +
               "FROM bookedhours b " +
               "WHERE date_format(b.date, '%b-%Y') = :month " + // WHERE comes first
               "GROUP BY date_format(b.date, '%b-%Y')",         // GROUP BY comes second
       nativeQuery = true)
List<ProjectHoursProjection> getProjectHoursByMonth(@Param("month") String month);

@Query(value = "DELETE FROM bookedhours where allotmentid=:allotmentid",nativeQuery = true)
public void deleteHoursByAllotmentId(@Param("allotmentid") int allotmentid);

@Query(
    value = """
        SELECT CONCAT(e.firstname, ' ', e.lastname) AS employeename,
               CASE e.employmentStatus
                   WHEN 0 THEN 'Separated'
                   ELSE 'Active'
               END AS status,
               YEAR(b.date) AS year,
               DATE_FORMAT(b.date, '%b-%Y') AS month,
               SUM(b.hoursbooked) AS hours
        FROM bookedhours b
        INNER JOIN employee_basic_info e ON e.eid = b.EID
        GROUP BY employeename, status, year, month, MONTH(b.date)
        ORDER BY year ASC, MONTH(b.date) ASC, status ASC, employeename ASC
        """,
    nativeQuery = true
)
List<ConsolidatedReportProjection> getConsolidatedReport();

@Query (value="""
    WITH RECURSIVE
    DateSeries (report_date) AS (
        SELECT DATE(:fromDate) AS report_date
        UNION ALL
        SELECT DATE_ADD(report_date, INTERVAL 1 DAY)
        FROM DateSeries
        WHERE report_date < DATE(:toDate)
    ),
    ProjectList AS (
        SELECT 
            t.pid,
            t.allotmentName as projectName,
            t.allotmentName,              
            t.totalHours,
            t.creationMonth,
            t.creation_raw,
            t.type,
            SUM(t.hours) AS hoursSoFar
        FROM (
            SELECT DISTINCT
                p.pid,
                p.allotmentName AS allotmentName,
                rp.projectname,
                p.totalHours,
                DATE_FORMAT(p.createdAt, '%b-%Y') AS creationMonth,
                DATE_FORMAT(p.createdAt, '%Y-%m') AS creation_raw,
                CASE 
                    WHEN prev.month_date IS NULL THEN 'Fresh'
                    ELSE 'Carried'
                END AS type,
                prev.hours
            FROM projects p
            INNER JOIN projectallocation pa ON pa.pid = p.pid
            INNER JOIN bookedhours b ON b.allotmentid = pa.allotmentid
            LEFT JOIN rootprojects rp ON rp.projId = p.rootId
            LEFT JOIN (
                SELECT 
                    pa_sub.pid,
                    DATE_FORMAT(b_sub.date, '%Y-%m') AS month_date, 
                    SUM(b_sub.hoursbooked) AS hours  
                FROM projectallocation pa_sub
                INNER JOIN bookedhours b_sub ON pa_sub.allotmentid = b_sub.allotmentid
                WHERE b_sub.date < :fromDate
                GROUP BY pa_sub.pid, DATE_FORMAT(b_sub.date, '%Y-%m')
            ) prev ON prev.pid = p.pid
            WHERE b.date >= :fromDate AND b.date <= :toDate
        ) t 
        GROUP BY 
            t.pid,
            t.projectname,
            t.allotmentName,              
            t.totalHours,
            t.type,
            t.creationMonth,
            t.creation_raw
    )
SELECT
    DATE_FORMAT(DS.report_date, '%d-%b-%Y') AS fullDate,
    PL.projectname AS rootProjectName,
    PL.allotmentName,
    PL.pid,
    rp.clientId,
    cd.clientname,
    CASE 
        WHEN PL.creation_raw = DATE_FORMAT(DS.report_date, '%Y-%m') THEN PL.totalHours 
        WHEN PL.creation_raw < DATE_FORMAT(DS.report_date, '%Y-%m') THEN (PL.totalHours - COALESCE(PL.hoursSoFar, 0)) 
        ELSE 0 
    END AS carriedhours,
    DATE_FORMAT(DS.report_date, '%b-%Y') AS Month,
    COALESCE(SUM(BH.hoursBooked), 0) AS totalHoursBooked,
    PL.type,
    CASE
        WHEN DS.report_date > CURDATE() THEN '' 
        WHEN COALESCE(SUM(BH.hoursBooked), 0) = 0 THEN '-'
        ELSE CAST(SUM(BH.hoursBooked) AS CHAR)
    END AS conditionalHoursDisplay
FROM DateSeries DS
CROSS JOIN ProjectList PL
LEFT JOIN projectallocation PA ON PA.pid = PL.pid
LEFT JOIN projects p ON p.pid = PL.pid
LEFT JOIN rootprojects rp ON rp.projId = p.rootId
LEFT JOIN clientdata cd ON cd.cid = rp.clientId
LEFT JOIN bookedhours BH ON BH.allotmentid = PA.allotmentid AND BH.`date` = DS.report_date
GROUP BY
    DS.report_date,
    PL.pid,
    PL.projectname,
    PL.allotmentName,
    PL.type,
    PL.creation_raw,
    PL.totalHours,
    PL.hoursSoFar,
    rp.clientId,
    cd.clientname
ORDER BY
    PL.projectname,
    DS.report_date,
    PL.type;
    
    """,nativeQuery = true)

List<DayWiseHoursProjection> getDayWisehoursProjections(@Param ("fromDate") String fromDate,@Param ("toDate") String toDate);

@Query (value = """
    WITH RECURSIVE DateSeries (report_date) AS 
( SELECT CURDATE() - INTERVAL 29 DAY AS report_date
UNION ALL
SELECT DATE_ADD(report_date, INTERVAL 1 DAY)
FROM DateSeries
WHERE report_date < CURDATE() )
SELECT DS.report_date AS fullDate, EB.EID, CONCAT(EB.firstname, ' ', EB.lastname) AS employeeName,
COALESCE(SUM(BH.hoursBooked), 0) AS totalHours, 
CASE WHEN DS.report_date > CURDATE() THEN '' 
WHEN COALESCE(SUM(BH.hoursBooked), 0) = 0 THEN 'A' 
ELSE CAST(SUM(BH.hoursBooked) AS CHAR) END AS conditionalHours 
FROM DateSeries DS JOIN employee_basic_info EB ON EB.EID = :eid
LEFT JOIN bookedhours BH ON BH.EID = EB.EID AND BH.`date` = DS.report_date
GROUP BY DS.report_date, EB.EID, EmployeeName ORDER BY DS.report_date DESC
    """,nativeQuery = true)
public  List<EmployeeAttendanceProjection> getEmployeeAttendanceProjections(@Param("eid") String eid);
}
