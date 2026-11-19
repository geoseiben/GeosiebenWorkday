package com.geosieben.gsbworkday.repository;
import com.geosieben.gsbworkday.dto.ActiveAllotments;
import com.geosieben.gsbworkday.dto.AllotmentReportProjection;
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
}
