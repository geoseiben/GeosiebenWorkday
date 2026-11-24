package com.geosieben.gsbworkday.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.geosieben.gsbworkday.dto.ClientWiseProjectResponseDto;
import com.geosieben.gsbworkday.dto.ProjectReportProjection;
import com.geosieben.gsbworkday.entity.Clients;
public interface ClientRepository extends JpaRepository<Clients,Integer> {
@Query("SELECT new com.geosieben.gsbworkday.dto.ClientWiseProjectResponseDto(" +
             "c.cid, " +
           "c.clientId, " +
           "c.clientName, " +
           "SUM(CASE WHEN rp.projectStatus = 0 THEN 1L ELSE 0L END), " + // Active
           "SUM(CASE WHEN rp.projectStatus = 2 THEN 1L ELSE 0L END), " + // Finished
           "SUM(CASE WHEN rp.projectStatus = 3 THEN 1L ELSE 0L END), " + // Withdrawn
           "COUNT(rp)) " +                                             // Total (COUNT *)
           "FROM RootProject rp " +
           "JOIN rp.client c " + 
           "GROUP BY c.cid,c.clientId, c.clientName")
    List<ClientWiseProjectResponseDto> getClientWiseProjectStats();

    @Query(
    value = """
        SELECT c.clientName,
               r.projectName,concat(e.firstName,' ',e.lastName) as projectLead,
               CASE
                   WHEN DATE_FORMAT(p.createdAt, '%b-%Y') = DATE_FORMAT(b.date, '%b-%Y') THEN 'New'
                   ELSE 'Cr'
               END AS allotmentType,
               CASE
                   WHEN DATE_FORMAT(c.createdOn, '%b-%Y') = DATE_FORMAT(b.date, '%b-%Y') THEN 'New'
                   ELSE 'Ex'
               END AS clientType,
               CASE
                   WHEN DATE_FORMAT(r.createdOn, '%b-%Y') = DATE_FORMAT(b.date, '%b-%Y') THEN 'New'
                   ELSE 'Cr'
               END AS projectType,
               p.allotmentName AS allotmentName,
               DATE_FORMAT(b.date, '%b-%Y') AS month,
               SUM(b.hoursbooked) AS hours
        FROM clientdata c
        INNER JOIN rootprojects r ON c.cid = r.clientId
        INNER JOIN projects p ON r.projId = p.rootid
        INNER JOIN projectallocation pa ON pa.pid = p.pid
        left join employee_basic_info e on e.eid=r.projectLead
        LEFT JOIN bookedhours b ON pa.allotmentid = b.allotmentid
        WHERE r.projectType = 'Project'
        GROUP BY c.clientName, r.projectName, allotmentType, clientType, projectType, allotmentName, DATE_FORMAT(b.date, '%b-%Y')
        """,
    nativeQuery = true
)
List<ProjectReportProjection> fetchProjectReport();
}
