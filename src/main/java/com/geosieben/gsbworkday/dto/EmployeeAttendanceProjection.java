package com.geosieben.gsbworkday.dto;

public interface EmployeeAttendanceProjection {
String getFullDate();
String getEID();
String getEmployeeName();
double getTotalHours();
String getConditionalHours();
}
