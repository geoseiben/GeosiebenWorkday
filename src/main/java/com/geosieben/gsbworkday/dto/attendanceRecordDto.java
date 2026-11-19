package com.geosieben.gsbworkday.dto;

import java.math.BigDecimal;

public interface attendanceRecordDto {
public String getFirstName();
public String getLastName();
public Integer getId();
public String getEID();
public String getAttendanceDate();
public String getStatus();
public BigDecimal getWorkingDay();
public String getLeaveType();
public BigDecimal getLeaveDays();
}
