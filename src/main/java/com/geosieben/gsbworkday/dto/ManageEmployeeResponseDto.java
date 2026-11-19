package com.geosieben.gsbworkday.dto;

import java.time.LocalDate;

public interface ManageEmployeeResponseDto {
String getEid();
String getFirstName();
String getLastName();
String getPhone();
String getEmail();
LocalDate getDob();
LocalDate getDateOfJoining();
String getStatus();
String getWorkMail();
String getManager();
String getDesignation();
String getDepartmentShortName();
String getImage();
int getIdIssued();
LocalDate getIssuedDate();
int getExperienceYears();
int getExperienceMonths();
int getExperienceDays();
}
