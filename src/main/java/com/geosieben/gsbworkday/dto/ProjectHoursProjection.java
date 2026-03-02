package com.geosieben.gsbworkday.dto;

import java.math.BigDecimal;

public interface ProjectHoursProjection {
    String getAllotmentName();
    BigDecimal getProdHours();
    BigDecimal getQcHours();
    BigDecimal getDeliveryHours();
    BigDecimal getTotalHours();
    BigDecimal getPilotHours();
}
