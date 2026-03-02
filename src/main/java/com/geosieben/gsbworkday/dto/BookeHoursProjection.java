package com.geosieben.gsbworkday.dto;

import java.math.BigDecimal;

public interface BookeHoursProjection {
    String getFirstName();
    String getLastName();
    BigDecimal getProdHours();
    BigDecimal getQcHours();
    BigDecimal getDeliveryHours();
    BigDecimal getTotalHours();
    BigDecimal getPilotHours();
}