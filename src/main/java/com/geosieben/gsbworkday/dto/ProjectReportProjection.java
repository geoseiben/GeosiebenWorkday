package com.geosieben.gsbworkday.dto;

import java.math.BigDecimal;

public interface ProjectReportProjection {
    String getClientName();
    String getProjectName();
    String getAllotmentType();
    String getClientType();
    String getProjectType();
    String getAllotmentName();
    String getMonth();
    BigDecimal getHours();
    String getProjectLead();
}
