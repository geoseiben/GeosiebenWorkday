package com.geosieben.gsbworkday.dto;

public interface EmployeeHoursProjection {

    String getFeederAlloted();

    String getMonth();

    Double getTargethours();

    Double getCarriedHours();

    String getType();

    Double getExtraHours();

    String getLastSubmittedDate();

    Double getCompletionPercentage();

    Double getHoursBooked();
}