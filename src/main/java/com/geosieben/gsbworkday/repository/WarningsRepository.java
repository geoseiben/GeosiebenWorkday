package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.geosieben.gsbworkday.dto.WarningPrerequsites;
import com.geosieben.gsbworkday.dto.WarningsProjection;
import com.geosieben.gsbworkday.entity.Warnings;

public interface WarningsRepository extends JpaRepository<Warnings,Integer> {
    @Query (value = "SELECT concat(e.firstname,' ',e.lastName) as employeename, "+
            " j.workmail as employeeMail, concat(m.firstname,' ',m.lastName) as managerName,  "+
            " mj.workmail as managerMail, j.reportingManager "+
            " FROM employee_basic_info e "+
            " JOIN employee_joining_info j on e.eid=j.eid "+
            " LEFT JOIN employee_basic_info m on j.ReportingManager=m.eid "+
            " LEFT JOIN employee_joining_info mj on m.eid=mj.eid "+
            " where e.eid=:eid",nativeQuery = true)
            public  WarningPrerequsites getWarnigPrerequsites(@Param ("eid") String eid);

   @Query(value = """
            SELECT
                CONCAT(e.firstname, ' ', e.lastname) AS employee,
                CONCAT(b.firstname, ' ', b.lastname) AS issuedBy,
                CONCAT(rm.firstname, ' ', rm.lastname) AS reportingManager,
                DATE_FORMAT(w.issuedOn, '%b-%Y') AS issuedMonth,
                DATE_FORMAT(w.warningDate, '%d-%b-%Y') AS warningDate,
                CONVERT_TZ(w.issuedOn, '+00:00', '+05:30') AS issuedOn,
                w.warningType,
                w.loginTime,
                w.logoutTime,
                w.breakHours,
                w.id,
                w.remarks
            FROM warnings w
            LEFT JOIN employee_basic_info e ON e.eid = w.issuedTo
            LEFT JOIN employee_basic_info b ON b.eid = w.issuedBy
            LEFT JOIN employee_joining_info j ON j.eid = w.issuedTo
            LEFT JOIN employee_basic_info rm ON rm.eid = CASE
                WHEN w.reportingManager IS NOT NULL THEN w.reportingManager
                ELSE j.ReportingManager
            END
            ORDER BY w.issuedOn DESC
            """, nativeQuery = true)
    List<WarningsProjection> findAllWarningProjections();

}
