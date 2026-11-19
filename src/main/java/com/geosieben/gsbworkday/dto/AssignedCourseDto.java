package com.geosieben.gsbworkday.dto;

import java.time.LocalDate;

public interface AssignedCourseDto {
int getCourseId();
int getAssignmentId();
String getCourseTitle();
String getCourseDescription();
String getCategory();
String getAssignedBy();
String getCourseImage();
String getProfeciencyLevel();
LocalDate getAssignedOn();
int getIsCompleted();
}
