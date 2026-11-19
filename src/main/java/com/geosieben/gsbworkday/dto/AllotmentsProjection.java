package com.geosieben.gsbworkday.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface AllotmentsProjection {
Integer getAllotmentid();
int getCompletionPercentage();
boolean getIsRunning();
String getType();
LocalDate getDeadline();
LocalDate getQcDeadline();
String getFeederAlloted();
Long getElapsedTime();
Long getStartTime();
BigDecimal getHrsAssigned();
BigDecimal getQcHrs();
}
