package com.geosieben.gsbworkday.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface AttendanceResponseDto {
 String getEid();
 String getFirstName();
 String getLastName();
 LocalDate getDate();
 String getStatus();
 BigDecimal getWorkingDay();
 BigDecimal getLeaveDays();
 String getLeaveType();
}
