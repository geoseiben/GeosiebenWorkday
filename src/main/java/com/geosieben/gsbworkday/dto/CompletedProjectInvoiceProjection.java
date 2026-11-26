package com.geosieben.gsbworkday.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public interface CompletedProjectInvoiceProjection {

    // --- Fields from projectinvoice (i) ---
    // Update these getters to match the exact columns you select from projectinvoice
    Long getInvoiceId();
    String getInvoiceNo();
    String getInvoiceStatus();

    // --- Fields from projects & joined tables ---
    Long getPid();
    String getEid();
    String getProjectId();
    String getProjectName();
    Double getTotalHours();       // Or Integer / BigDecimal depending on DB column
    String getStartdate();        // Formatted string ('%d-%b-%y')
    String getEnddate();          // Formatted string ('%d-%b-%y')
    LocalDate getSd();            // Raw p.startdate (use LocalDateTime if DATETIME)
    LocalDate getEd();            // Raw p.enddate (use LocalDateTime if DATETIME)
    String getProjectLead();
    String getCreatedBy();
    String getDepartment();
    String getCreatedAt();        // Formatted string ('%d-%b-%Y')
    LocalDateTime getFinishedOn(); // Raw p.finishedOn (use LocalDate if DATE)
    String getClientId();           // Or String depending on clientdata.clientId type
    String getFinDate();          // Formatted string ('%d-%b-%y')
    String getStatus();
}
