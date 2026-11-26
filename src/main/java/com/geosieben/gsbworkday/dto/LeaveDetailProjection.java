package com.geosieben.gsbworkday.dto;

public interface LeaveDetailProjection {
    Long getId();
    String getEid();
    String getFromDate();
    String getToDate();
    Double getNoofdays(); // Use Integer or BigDecimal depending on your DB column type
    String getDescription();
    String getLeaveType();
    String getStatus();
}