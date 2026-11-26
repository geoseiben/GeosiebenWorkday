package com.geosieben.gsbworkday.repository;
import com.geosieben.gsbworkday.dto.ActiveAllotments;
import com.geosieben.gsbworkday.dto.AllotmentReportProjection;
import com.geosieben.gsbworkday.dto.CompletedProjectInvoiceProjection;
import com.geosieben.gsbworkday.dto.MyProjectProjection;
import com.geosieben.gsbworkday.entity.*;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
public interface ProjectRepository extends JpaRepository<Project,Integer>{
@Query(value="SELECT * from projects where rootid=?1",nativeQuery=true)
Project findProjectByRootid(int rootid);

@Query(value="SELECT * from projects where rootid=?1",nativeQuery=true)
List<Project> findProjectsByRootProjectId(int rootid);

@Query(value = "select t.projectname from (select b.eid ,p.allotmentName as projectname "+
    "  from bookedhours b "+
    "INNER JOIN projectallocation pa on pa.allotmentid=b.allotmentid "+
    "INNER JOIN projects p on p.pid=pa.pid "+
    "INNER JOIN rootprojects rp on rp.projid=p.rootId "+
    " where p.status<2 and rp.projectType='Project' "+
    " group by b.eid,p.allotmentName) t where eid=:eid ",nativeQuery = true
)
public List<ActiveAllotments> getActiveProjects(@Param("eid") String eid);

@Query(value="SELECT rp.projectname,p.allotmentname AS allotmentname,"+
    " cd.clientId,DATE_FORMAT(b.date, '%b-%Y') AS month,"+
    "CASE "+ 
        " WHEN p.createdAt >= DATE_FORMAT(b.date, '%Y-%m-01') THEN 'Fresh' "+ 
        " ELSE 'Carried' "+
    " END AS projectType,"+
    " CONCAT(e.firstname, ' ', e.lastname) AS projectLead, "+
    " CASE "+
        " WHEN p.status IN (0, 1) THEN 'Active' "+
        " WHEN p.status = 2 THEN 'Finished' "+
        " ELSE 'Other' "+
    " END AS projectStatus,"+
    " SUM(b.hoursbooked) AS hours "+
" FROM bookedhours b "+
" INNER JOIN projectallocation pa ON pa.allotmentid = b.allotmentid "+
" INNER JOIN projects p ON p.pid = pa.pid "+
" INNER JOIN rootprojects rp ON rp.projId = p.rootId "+
" LEFT JOIN clientdata cd ON cd.cid = rp.clientId "+
" LEFT JOIN employee_basic_info e ON e.eid = p.projectLead "+
" WHERE rp.projectType <> 'Pilot' "+
" GROUP BY "+
    " rp.projectname,  "+
    "  p.allotmentname,  "+
    "  cd.clientId,  "+
    "  DATE_FORMAT(b.date, '%Y-%m'), "+
    "  DATE_FORMAT(b.date, '%b-%Y'), "+
    "  DATE_FORMAT(b.date, '%Y-%m-01'), "+
    " p.createdAt, "+
    " p.status, "+
    "  e.firstname, "+
    "  e.lastname "+
"ORDER BY clientId ASC, projectname;",nativeQuery=true)
public List<AllotmentReportProjection> getAllotmentsReport();

@Query(value = """
        SELECT 
            p.pid,
            pl.EID AS eid,
            p.allotmentName,
            p.totalHours,
            DATE_FORMAT(p.startDate, '%d-%b-%Y') AS startdate,
            DATE_FORMAT(p.enddate, '%d-%b-%Y') AS enddate,
            CONCAT(pl.firstname, ' ', pl.lastname) AS projectLeadd,
            CONCAT(cb.firstname, ' ', cb.lastname) AS createdBy,
            d.DepartmentShortName AS department,
            DATE_FORMAT(p.finishedOn, '%d-%b-%Y') AS completionDate,
            DATE_FORMAT(CONVERT_TZ(p.createdAt, '+00:00', '+05:30'), '%d-%b-%Y') AS createdAt,
            CASE p.status
                WHEN 0 THEN 'Created'
                WHEN 1 THEN 'In-Progress'
                WHEN 2 THEN 'Completed'
                WHEN 3 THEN 'On Hold'    
                WHEN 4 THEN 'Withdrawn'
            END AS status,
            COALESCE(bh.bookedhours, 0) AS bookedhours
        FROM projects p 
        INNER JOIN employee_basic_info pl ON p.projectLead = pl.EID 
        INNER JOIN employee_basic_info cb ON p.createdBy = cb.EID 
        LEFT JOIN departments d ON p.department = d.id
        LEFT JOIN rootprojects rp ON p.rootid = rp.projid
        LEFT JOIN (
            SELECT pa.pid, SUM(b.hoursbooked) AS bookedhours
            FROM projectallocation pa
            INNER JOIN bookedhours b ON b.allotmentid = pa.allotmentid
            GROUP BY pa.pid
        ) bh ON bh.pid = p.pid
        WHERE p.status NOT IN (3, 4)
          AND p.projectLead = :leadId
          AND (rp.projectType IS NULL OR rp.projectType <> 'Pilot')
        ORDER BY p.status ASC, p.pid DESC
        """, nativeQuery = true)
    List<MyProjectProjection> findActiveProjectsByLead(@Param("leadId") String leadId);

    @Query(value="""
        SELECT i.*,p.pid,pl.eid,pl.EID,p.projectId,p.allotmentName as projectName,p.totalHours,date_format(p.startdate,'%d-%b-%y') as startdate,
            date_format(p.enddate,'%d-%b-%y') as enddate,p.startdate as sd,p.enddate as ed,
                        CONCAT(pl.firstname,' ',pl.lastname) AS projectLead,
                        CONCAT(cb.firstname,' ',cb.lastname) AS createdBy,
                        d.DepartmentShortName AS department,
                        DATE_FORMAT(CONVERT_TZ(p.createdAt, '+00:00', '+05:30'),'%d-%b-%Y') AS createdAt,
                        p.finishedOn,
                        cd.clientId,
                         date_format(p.finishedOn,'%d-%b-%y') as finDate,
                        CASE p.status
                                WHEN 0 THEN 'Created'
                                WHEN 1 THEN 'In-Progress'
                                WHEN 2 THEN 'Completed'
                                WHEN 3 THEN 'On Hold'
                                WHEN 4 THEN 'Withdrawn'
                        END AS status
                    FROM projects p 
                    INNER JOIN rootprojects rp on p.rootId =rp.projId
                    INNER JOIN clientdata cd on cd.cid=rp.clientId
                    INNER JOIN employee_basic_info pl ON p.projectLead=pl.EID 
                    INNER JOIN employee_basic_info cb ON p.createdBy=cb.EID 
                     left JOIN projectinvoice i on i.projectid=p.pid
                    left JOIN departments d ON p.department=d.id where p.status=2
        """,nativeQuery =  true)
        public  List<CompletedProjectInvoiceProjection> getFinishedProjectsProjections();


}
