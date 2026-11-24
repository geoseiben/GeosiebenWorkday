package com.geosieben.gsbworkday.dto;

public interface DayWiseHoursProjection {
String getfullDate();
String getRootProjectName();
String getAllotmentName();
int getPid();
int getClientId();
String getClientName();
double getCarriedHours();
String getMonth();
double getTotalHoursBooked();
String getType();
String getConditionalHoursDisplay();
}
