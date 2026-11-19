package com.geosieben.gsbworkday.dto;

import java.time.LocalDateTime;

public interface CourseAssesmentDto {
int getAssesmentid();
String getAssesmentDescription();
String getAssesmentTitle();
LocalDateTime getCreatedOn();
int getDurationInMinutes();
int getPassingScore();
int getQuestions();
}
