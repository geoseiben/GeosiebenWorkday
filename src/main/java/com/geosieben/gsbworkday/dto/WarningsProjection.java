package com.geosieben.gsbworkday.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

public interface WarningsProjection {
    Long getId();
    String getEmployee();
    String getIssuedBy();
    String getReportingManager();
    String getIssuedMonth();
    String getWarningDate();
    LocalDateTime getIssuedOn();
    String getWarningType();
    LocalTime getLoginTime();
    LocalTime getLogoutTime();
    LocalTime getBreakHours();
    String getRemarks();
}