package com.geosieben.gsbworkday.dto;

public interface MyProjectProjection {

    Integer getPid();                 // p.pid (Use Long if pid is BIGINT)
    
    String getEid();                  // pl.eid / pl.EID
    
    String getAllotmentName();        // p.allotmentName
    
    Double getTotalHours();           // p.totalHours (Use BigDecimal or Integer depending on column type)
    
    String getStartdate();            // DATE_FORMAT(p.starDate, '%d-%b-%Y') AS startdate
    
    String getEnddate();              // DATE_FORMAT(p.enddate, '%d-%b-%Y') AS enddate
    
    String getProjectLeadd();         // CONCAT(...) AS projectLeadd (preserved double 'd' from your alias)
    
    String getCreatedBy();            // CONCAT(...) AS createdBy
    
    String getDepartment();           // d.DepartmentShortName AS department
    
    String getCompletionDate();       // DATE_FORMAT(p.finishedOn, '%d-%b-%Y') AS completionDate
    
    String getCreatedAt();            // DATE_FORMAT(...) AS createdAt
    
    String getStatus();               // CASE p.status ... END AS status
    
    Double getBookedhours();          // COALESCE(bh.bookedhours, 0) AS bookedhours (Use BigDecimal if DECIMAL)
}
