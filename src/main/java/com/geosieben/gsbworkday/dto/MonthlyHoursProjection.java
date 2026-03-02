package com.geosieben.gsbworkday.dto;

import java.math.BigDecimal;

public interface MonthlyHoursProjection {
    String getMonthName();
    BigDecimal getProdHours();
    BigDecimal getQcHours();
    BigDecimal getDeliveryHours();
    BigDecimal getTotalHours();
    BigDecimal getPilotHours();
}
