package com.geosieben.gsbworkday.dto;

import java.time.LocalDate;

public interface LeaveProjection {
    Long getId();
    Double getNoofDays();
    LocalDate getFromDate();
    LocalDate getToDate();
    String getLeaveType();
    String getFromsession();
    String getTosession();
    String getDescription();
    String getEid();
    String getFromdatee();
    String getTodatee();
    String getReportingmanager();
    String getEmployeeName();
    String getManager();
    String getAppliedOn();
    String getApprovedOn();
    String getApprovedBy();
    String getDepartment();
    Integer getStatus();
    String getCoStatus();
}
