package com.geosieben.gsbworkday.dto;

import java.time.LocalDate;

public interface SeparationRequestsProjection {
int getSeparationid();
String getReasonforLeaving();
LocalDate getResignationDate();
 String getSeparationType();
 String getStatus();
 LocalDate getLastWorkingDay();
String getFirstName();
String getLastName();
String getEID();
LocalDate getDateOfJoining();
String getAdminRemarks();
}
